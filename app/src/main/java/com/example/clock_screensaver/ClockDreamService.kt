package com.example.clock_screensaver

import android.service.dreams.DreamService

class ClockDreamService : DreamService() {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        isInteractive = false
        isFullscreen = true
        setContentView(R.layout.clock_saver)
    }
}