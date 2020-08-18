package com.udacity.bakingapp.utils;

public class MediaUtils {
    public static boolean isVideo(String videoUrl) {
        String extension = videoUrl.substring(videoUrl.lastIndexOf("."));

        return extension.equalsIgnoreCase(".mp4") ||
                extension.equalsIgnoreCase(".mkv") ||
                extension.equalsIgnoreCase(".m4a");
    }

    public static boolean isImage(String thumbnailUrl) {
        String extension = thumbnailUrl.substring(thumbnailUrl.lastIndexOf("."));

        return extension.equalsIgnoreCase(".jpeg") ||
                extension.equalsIgnoreCase(".png") ||
                extension.equalsIgnoreCase(".bmp") ||
                extension.equalsIgnoreCase(".webp") ||
                extension.equalsIgnoreCase(".gif");
    }

    public static int getOptimumHeightInPxForVideo(int width) {
        return Math.round((float)width * 9 / 16);
    }
}
