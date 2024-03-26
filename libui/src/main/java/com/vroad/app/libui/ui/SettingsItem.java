package com.vroad.app.libui.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SwitchCompat;

import com.elvishew.xlog.XLog;
import com.vroad.app.libui.R;
import com.vroad.app.libui.utils.UtilsUI;


public class SettingsItem extends RelativeLayout {
  private static final int DEFAULT_ICON_SIZE = 16;
  private static final float DEFAULT_TEXT_SIZE = 16;
  private static final int DEFAULT_COLOR = Color.LTGRAY;
  private static final int DEFAULT_LEFT_ICON_MARGIN_LEFT = 16;
  private static final int DEFAULT_LEFT_ICON_MARGIN_RIGHT = 16;
  private static final int DEFAULT_RIGHT_TEXT_MARGIN_RIGHT = 8;
  private static final int DEFAULT_RIGHT_ICON_MARGIN_RIGHT = 8;
  private View root;
  private OnClick onClick;
  private boolean checked;
  private int currentRightIcon;
  private int rightIconType;
  private Integer rightIconMarginLeft, rightIconSize;
  private Drawable rightIcon;

  public SettingsItem(Context context) {
    this(context, null);
  }

  public SettingsItem(Context context, AttributeSet attributeSet) {
    this(context, attributeSet, 0);
  }

  public SettingsItem(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attributeSet) {
    try {
      root = View.inflate(context, R.layout.settings_item, this);
      if (root == null)
        return;
      TypedArray attrs = context.obtainStyledAttributes(attributeSet, R.styleable.SettingsItem);
      for (int i = 0; i < attrs.getIndexCount(); i++) {
        setAttributes(attrs, attrs.getIndex(i));
      }
      attrs.recycle();
      checkAttributes();
      registerListeners();
    } catch (Exception ignored) {
    }
  }

  private void setAttributes(TypedArray attrs, int i) {
    boolean visible = false;
    try {
      /*------------------ left-icon -----------------*/
      if (i == R.styleable.SettingsItem_left_icon) {
        UtilsUI.setIcon(root, R.id.left_icon, attrs.getDrawable(i));
      } else if (i == R.styleable.SettingsItem_left_icon_size) {
        UtilsUI.setSize(root, R.id.left_icon, attrs.getInt(i, DEFAULT_ICON_SIZE));
      } else if (i == R.styleable.SettingsItem_left_icon_margin_left) {
        UtilsUI.setMargin(root, R.id.left_icon,
            0, false, 0, false, 0, false,
            attrs.getInt(i, DEFAULT_LEFT_ICON_MARGIN_LEFT), true);
      } else if (i == R.styleable.SettingsItem_left_icon_margin_right) {
        UtilsUI.setMargin(root, R.id.left_text,
            0, false, 0, false, 0, false,
            attrs.getInt(i, DEFAULT_LEFT_ICON_MARGIN_RIGHT), true);
        UtilsUI.setMargin(root, R.id.divider,
            0, false, 0, false, 0, false,
            attrs.getInt(i, DEFAULT_LEFT_ICON_MARGIN_RIGHT), true);
      }
      /*------------------ left-text -----------------*/
      else if (i == R.styleable.SettingsItem_left_text) {
        UtilsUI.setText(root, R.id.left_text, attrs.getString(i));
      } else if (i == R.styleable.SettingsItem_left_text_size) {
        UtilsUI.setTextSize(root, R.id.left_text, attrs.getFloat(i, DEFAULT_TEXT_SIZE));
      } else if (i == R.styleable.SettingsItem_left_text_color) {
        UtilsUI.setColor(root, R.id.left_text, attrs.getColor(i, DEFAULT_COLOR));
      }
      /*------------------ right-text -----------------*/
      else if (i == R.styleable.SettingsItem_right_text) {
        UtilsUI.setText(root, R.id.right_text, attrs.getString(i));
      } else if (i == R.styleable.SettingsItem_right_text_color) {
        UtilsUI.setColor(root, R.id.right_text, attrs.getColor(i, DEFAULT_COLOR));
      } else if (i == R.styleable.SettingsItem_right_text_size) {
        UtilsUI.setTextSize(root, R.id.right_text, attrs.getFloat(i, DEFAULT_TEXT_SIZE - 2));
      } else if (i == R.styleable.SettingsItem_right_text_margin_right) {
        UtilsUI.setMargin(root, R.id.right_text,
            0, false, attrs.getInt(i, DEFAULT_RIGHT_TEXT_MARGIN_RIGHT), true, 0, false, 0, false);
      }
      /*------------------ right-icon -----------------*/
      else if (i == R.styleable.SettingsItem_right_icon_type) {
        rightIconType = attrs.getInt(i, 4);
        if (rightIconType < 4)
          setRightIconVisibility(rightIconType == 0 ? R.id.check_box : rightIconType == 1 ? R.id.switcher : R.id.default_right_icon);
      } else if (i == R.styleable.SettingsItem_right_icon) {
        rightIcon = attrs.getDrawable(i);
      } else if (i == R.styleable.SettingsItem_right_icon_size) {
        rightIconSize = attrs.getInt(i, DEFAULT_ICON_SIZE);
      } else if (i == R.styleable.SettingsItem_right_icon_margin_right) {
        rightIconMarginLeft = attrs.getInt(i, DEFAULT_RIGHT_ICON_MARGIN_RIGHT);
      }
      /*------------------ under-line -----------------*/
      else if (i == R.styleable.SettingsItem_underline) {
        UtilsUI.setVisibility(root, R.id.divider, attrs.getBoolean(i, false));
      } else if (i == R.styleable.SettingsItem_underline_color) {
        UtilsUI.setColor(root, R.id.divider, attrs.getColor(i, DEFAULT_COLOR));
      } else if (i == R.styleable.SettingsItem_underline_size) {
        UtilsUI.setSize(root, R.id.divider, attrs.getInt(i, 1), true, 0, false);
      }
    } catch (Exception ignored) {
    }
  }

  private void setRightIconVisibility(int visibleIcon) {
    currentRightIcon = visibleIcon;
    int[] icons = new int[]{R.id.check_box, R.id.switcher, R.id.default_right_icon};
    UtilsUI.setVisibility(root.findViewById(R.id.right_icon_container), true);
    for (int icon : icons) {
      UtilsUI.setVisibility(root.findViewById(icon), icon == visibleIcon);
    }
  }

  private void checkAttributes() {
    /* "right-icon" could be set manually only when "right-icon-type" is "custom (3)"  */
    if (rightIconType == 3) {
      UtilsUI.setIcon(root.findViewById(R.id.default_right_icon), rightIcon);
    }
    if (rightIconSize != null) {
      UtilsUI.setSize(root, currentRightIcon, rightIconSize);
    }
    if (rightIconMarginLeft != null) {
      UtilsUI.setMargin(root, currentRightIcon,
          0, false, rightIconMarginLeft, true, 0, false, 0, false);
    }
  }

  private void registerListeners() {
    root.setOnClickListener(view -> {
      if (onClick != null)
        onClick.click((checked));
    });
    ((CheckBox) root.findViewById(R.id.check_box)).setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (onClick != null)
        onClick.click(isChecked);
    });
    ((SwitchCompat) root.findViewById(R.id.switcher)).setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (onClick != null)
        onClick.click(isChecked);
    });
  }

  private void switchCheckStatus() {
    if (currentRightIcon == R.id.default_right_icon && onClick != null)
      onClick.click(checked);
    else if (currentRightIcon == R.id.check_box) {
      CheckBox checkBox = (CheckBox) root.findViewById(R.id.check_box);
      checkBox.setChecked(!checkBox.isChecked());
      checked = checkBox.isChecked();
    } else if (currentRightIcon == R.id.switcher) {
      SwitchCompat switcher = (SwitchCompat) root.findViewById(R.id.switcher);
      switcher.setChecked(!switcher.isChecked());
      checked = switcher.isChecked();
    }
  }

  public interface OnClick {
    void click(boolean isChecked);
  }
}

