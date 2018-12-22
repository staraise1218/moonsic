package cn.baby.happyball.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.widget.Filter;

/**
 * @author DRH
 * @data 2018/11/14
 */
public class AlphaFilter extends Filter {
    public static final int INSIDE_MODE = 0;
    public static final int EDGE_MODE = 1;
    public static final int OUTSIDE_MODE = 2;

    private static int findNonOpaque(int x, int y, int width, int height, int[] srcPixels) {
        if (width < height) {
            return findNonOpaqueByX(x, y, width, height, srcPixels);
        } else {
            return findNonOpaqueByY(x, y, width, height, srcPixels);
        }
    }

    /**
     * 横向检测左右两侧边界
     */
    private static int findNonOpaqueByX(int x, int y, int width, int height, int[] srcPixels) {
        int mode = OUTSIDE_MODE;
        //当前点左右两侧均有边界点出现，则认为当前点在内部或者边框中
        if (findNonOpaqueByXLeft(x, y, width, height, srcPixels) && findNonOpaqueByXRight(x, y, width, height, srcPixels)) {
            mode = INSIDE_MODE;
        }
        int pos = y * width + x;
        if (isMatch(pos, srcPixels)) {
            mode = EDGE_MODE;
        }
        return mode;
    }

    /**
     * 检测与当前点y坐标相同的左侧各点是否有边界存在
     */
    private static boolean findNonOpaqueByXLeft(int x, int y, int width, int height, int[] srcPixels) {
        for (int i = 0; i < x; i++) {
            int pos = y * width + i;
            if (isMatch(pos, srcPixels)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测与当前点y坐标相同的右侧各点是否有边界存在
     */
    private static boolean findNonOpaqueByXRight(int x, int y, int width, int height, int[] srcPixels) {
        for (int i = x + 1; i < width; i++) {
            int pos = y * width + i;
            if (isMatch(pos, srcPixels)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 纵向检测上下两侧的边界
     */
    private static int findNonOpaqueByY(int x, int y, int width, int height, int[] srcPixels) {
        int mode = OUTSIDE_MODE;
        //当前点上下两侧均有边界点出现，则认为当前点在内部或者边框中
        if (findNonOpaqueByYTop(x, y, width, height, srcPixels) && findNonOpaqueByYBottom(x, y, width, height, srcPixels)) {
            mode = INSIDE_MODE;
        }
        int pos = y * width + x;
        if (isMatch(pos, srcPixels)) {
            mode = EDGE_MODE;
        }
        return mode;
    }

    /**
     * 检测与当前点x坐标相同的上方各点是否有边界存在
     */
    private static boolean findNonOpaqueByYTop(int x, int y, int width, int height, int[] srcPixels) {
        for (int i = 0; i < y; i++) {
            int pos = i * width + x;
            if (isMatch(pos, srcPixels)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测与当前点x坐标相同的下方各点是否有边界存在
     */
    private static boolean findNonOpaqueByYBottom(int x, int y, int width, int height, int[] srcPixels) {
        for (int i = y + 1; i < height; i++) {
            int pos = i * width + x;
            if (isMatch(pos, srcPixels)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMatch(int pos, int[] srcPixels) {
        int color = srcPixels[pos];
        int alpha = Color.alpha(color);
        //检测是否是边界，针对背景图片选用透明度进行过滤
        if (alpha >= 94 && alpha < 255) {
            return true;
        }
        return false;
    }

    /**
     * 图片效果叠加
     *
     * @param bmp    要裁剪的图片
     * @param filter 边框
     * @return
     */
    public static Bitmap overlay(Bitmap bmp, Bitmap filter) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap overlay = filter;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
        bitmap.setHasAlpha(true);

        // 对边框图片进行缩放
        int w = overlay.getWidth();
        int h = overlay.getHeight();
        float scaleX = width * 1F / w;
        float scaleY = height * 1F / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix, true);

        int[] srcPixels = new int[width * height];
        int[] layPixels = new int[width * height];
        bmp.getPixels(srcPixels, 0, width, 0, 0, width, height);
        overlayCopy.getPixels(layPixels, 0, width, 0, 0, width, height);

        int pos = 0;
        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                pos = i * width + k;

                int mode = findNonOpaque(k, i, width, height, layPixels);
                if (mode == INSIDE_MODE) {
                    srcPixels[pos] = srcPixels[pos];
                    continue;
                } else if (mode == EDGE_MODE) {
                    srcPixels[pos] = layPixels[pos];
                } else {
                    srcPixels[pos] = 0;
                    continue;
                }
            }
        }
        bitmap.setPixels(srcPixels, 0, width, 0, 0, width, height);
        overlay.recycle();
        overlayCopy.recycle();
        return bitmap;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        return null;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        //do nothings
    }

    public static Bitmap processBitmap(Bitmap bitmap, Bitmap frame) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(resultBitmap);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        canvas.drawBitmap(frame, 0, 0, null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(bitmap, 0 ,0, paint);
        return resultBitmap;
    }

}
