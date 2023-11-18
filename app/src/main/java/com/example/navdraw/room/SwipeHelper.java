package com.example.navdraw.room;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    //    public static final int BUTTON_WIDTH = YOUR_WIDTH_IN_PIXEL_PER_BUTTON
    //public static final int BUTTON_WIDTH = Constants.SWIPE_RECYCLERVIEW_BUTTONS_SIZE; // 56
    public static float TEXT_SIZE_48 = 48f;
    public static float TEXT_SIZE_24 = 24f;

    public static int BUTTON_WIDTH_128 = 128;
    public static int BUTTON_WIDTH_192 = 192;

    public static String COLOR_PRIMARY = "#428BCA";
    public static String COLOR_DANGER = "#d9534f";
    public static String COLOR_INFO = "#5bc0de";
    public static String COLOR_SUCCES = "#5cb85c";
    public static String COLOR_WARNING = "#f0ad4e";
    public static String COLOR_REGULAR = "#999999"; // #C7C7CB
    public static String COLOR_TRANSPARENT = "#00000000";

    //    private int buttonwidth = Constants.SWIPE_RECYCLERVIEW_BUTTONS_SIZE_128; // 56
    private int buttonwidth = BUTTON_WIDTH_128; // 56
    private RecyclerView recyclerView;
    private List<UnderlayButton> buttons;
    private GestureDetector gestureDetector;
    private int swipedPos = -1;
    private float swipeThreshold = 0.5f;
    private Map<Integer, List<UnderlayButton>> buttonsBuffer;
    private Queue<Integer> recoverQueue;

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            for (UnderlayButton button : buttons) {
                if (button.onClick(e.getX(), e.getY()))
                    break;
            }

            return true;
        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent e) {
            if (swipedPos < 0) return false;
            Point point = new Point((int) e.getRawX(), (int) e.getRawY());

            RecyclerView.ViewHolder swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPos);
            if (swipedViewHolder != null) {
                View swipedItem = swipedViewHolder.itemView;
                Rect rect = new Rect();
                swipedItem.getGlobalVisibleRect(rect);

                if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_MOVE) {
                    if (rect.top < point.y && rect.bottom > point.y)
                        gestureDetector.onTouchEvent(e);
                    else {
                        recoverQueue.add(swipedPos);
                        swipedPos = -1;
                        recoverSwipedItem();
                    }
                }
            }
            return false;
        }
    };

    public SwipeHelper(Context context, Integer buttonWidth, RecyclerView recyclerView) {
        super(0, ItemTouchHelper.LEFT);
        this.buttonwidth = buttonWidth;
        this.recyclerView = recyclerView;
        this.buttons = new ArrayList<>();
        this.gestureDetector = new GestureDetector(context, gestureListener);
        this.recyclerView.setOnTouchListener(onTouchListener);
        buttonsBuffer = new HashMap<>();
        recoverQueue = new LinkedList<Integer>() {
            @Override
            public boolean add(Integer o) {
                if (contains(o))
                    return false;
                else
                    return super.add(o);
            }
        };

        attachSwipe();
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();

        if (swipedPos != pos)
            recoverQueue.add(swipedPos);

        swipedPos = pos;

        if (buttonsBuffer.containsKey(swipedPos))
            buttons = buttonsBuffer.get(swipedPos);
        else
            buttons.clear();

        buttonsBuffer.clear();
//        swipeThreshold = 0.5f * buttons.size() * BUTTON_WIDTH;
        swipeThreshold = 0.5f * buttons.size() * buttonwidth;
        recoverSwipedItem();
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return swipeThreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f * defaultValue;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return 5.0f * defaultValue;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int pos = viewHolder.getAdapterPosition();
        float translationX = dX;
        View itemView = viewHolder.itemView;

        if (pos < 0) {
            swipedPos = pos;
            return;
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                List<UnderlayButton> buffer = new ArrayList<>();

                if (!buttonsBuffer.containsKey(pos)) {
                    instantiateUnderlayButton(viewHolder, buffer);
                    buttonsBuffer.put(pos, buffer);
                } else {
                    buffer = buttonsBuffer.get(pos);
                }

//                translationX = dX * buffer.size() * BUTTON_WIDTH / itemView.getWidth();
                translationX = dX * buffer.size() * buttonwidth / itemView.getWidth();
                drawButtons(c, itemView, buffer, pos, translationX);
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
    }

    private synchronized void recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            int pos = recoverQueue.poll();
            if (pos > -1) {
                recyclerView.getAdapter().notifyItemChanged(pos);
            }
        }
    }

    private void drawButtons(Canvas c, View itemView, List<UnderlayButton> buffer, int pos, float dX) {
        float right = itemView.getRight();
        float dButtonWidth = (-1) * dX / buffer.size();

        for (UnderlayButton button : buffer) {
            float left = right - dButtonWidth;
            button.onDraw(
                    c,
                    new RectF(
                            left,
                            itemView.getTop(),
                            right,
                            itemView.getBottom()
                    ),
                    pos
            );

            right = left;
        }
    }

    public void attachSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public abstract void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons);

    public static class UnderlayButton {
        private Context context;
        private String text;
        private float textSize;
        private int textColor;
        private int imageResId;
        private int backgroundColor;
        private int pos;
        private RectF clickRegion;
        private UnderlayButtonClickListener clickListener;

        public UnderlayButton(Context context, String text, float textSize, int textColor, int imageResId, int backgroundColor, UnderlayButtonClickListener clickListener) {
            this.context = context;
            this.text = text;
            this.textSize = textSize;
            this.textColor = textColor;
            this.imageResId = imageResId;
            this.backgroundColor = backgroundColor;
            this.clickListener = clickListener;
        }

        public boolean onClick(float x, float y) {
            if (clickRegion != null && clickRegion.contains(x, y)) {
                clickListener.onClick(pos);
                return true;
            }

            return false;
        }

        public void onDraw(Canvas c, RectF rect, int pos) {

//--------------------------------------------------------------------------------------------------
//DRAW AND COLOR RECT
            Rect r = new Rect();
            float cHeight = rect.height();
            float cWidth = rect.width();
            Paint p = new Paint();
            // Draw background
            p.setColor(backgroundColor);
            c.drawRect(rect, p);

//--------------------------------------------------------------------------------------------------
//TEXT
            if (text != null) {
                // Draw Text
                p.setColor(textColor);
                p.setTextSize(textSize);
                p.setTextAlign(Paint.Align.LEFT);
                p.getTextBounds(text, 0, text.length(), r);
                float x = cWidth / 2f - r.width() / 2f - r.left;
                float y = cHeight / 2f + r.height() / 2f - r.bottom;
                c.drawText(text, rect.left + x, rect.top + y, p);
            }
//--------------------------------------------------------------------------------------------------
//IMAGE
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResId);
            if (bitmap != null) {
                Paint pImage = new Paint();
                int bitmapWidth = bitmap.getWidth();
                int bitmapHeight = bitmap.getHeight();
                float xImage = cWidth / 2f - bitmapWidth / 2f - r.left;
                float yImage = cHeight / 2f - bitmapHeight / 2f - r.bottom;

                c.drawBitmap(bitmap, rect.left + xImage, rect.top + yImage, pImage);
            }
//--------------------------------------------------------------------------------------------------
            clickRegion = rect;
            this.pos = pos;
        }
    }

    public interface UnderlayButtonClickListener {
        void onClick(int pos);
    }
}

