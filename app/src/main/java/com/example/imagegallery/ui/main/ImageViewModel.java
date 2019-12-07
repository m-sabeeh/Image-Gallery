package com.example.imagegallery.ui.main;

import com.example.imagegallery.models.Hit;

public class ImageViewModel {
    private Integer previewWidth;
    private String previewURL;
    private Integer previewHeight;

   void bind(Hit hit) {
        this.previewWidth = hit.getPreviewWidth();
        this.previewHeight = hit.getPreviewHeight();
        this.previewURL = hit.getPreviewURL();
    }

    public Integer getPreviewWidth() {
        return previewWidth;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public Integer getPreviewHeight() {
        return previewHeight;
    }
}
