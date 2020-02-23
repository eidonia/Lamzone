package com.bast.lamzone.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bast.lamzone.R;

@SuppressLint("AppCompatCustomView")
public class ExpandableText extends TextView {

    private static final int DEFAULT_TRIM_LENGTH = 200;
    private static final String ELLIPSIS = ".....";

    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;

    public ExpandableText(Context context) {
        this(context, null);
    }

    public ExpandableText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableText);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableText_trimLength, DEFAULT_TRIM_LENGTH);
        typedArray.recycle();

        setOnClickListener(v -> {
            trim = !trim;
            setText();
            requestFocusFromTouch();
        });
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS);
        } else {
            return originalText;
        }
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public int getTrimLength() {
        return trimLength;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }
}