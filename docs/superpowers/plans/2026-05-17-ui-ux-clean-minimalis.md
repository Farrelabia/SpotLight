# SpotLight UI/UX Clean Minimalis — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Polish all SpotLight app screens to a clean minimalis style with soft indigo palette, more whitespace, and modern rounded corners — without restructuring any layouts.

**Architecture:** Purely visual refinement of existing XML layouts + resource files. No structural layout changes. Colors, dimensions, drawables, and minor Kotlin color references are updated.

**Tech Stack:** Android XML views, Material Design 3 components, Kotlin

**Spec:** `docs/superpowers/specs/2026-05-17-ui-ux-clean-minimalis-design.md`

---

## File Map

| Action | File | Purpose |
|---|---|---|
| Modify | `app/src/main/res/values/colors.xml` | New indigo color palette |
| Modify | `app/src/main/res/values/dimens.xml` | Updated spacing values |
| Modify | `app/src/main/res/values/themes.xml` | White toolbar, status bar color |
| Create | `app/src/main/res/drawable/bg_splash_gradient.xml` | Splash gradient background |
| Create | `app/src/main/res/drawable/bg_drag_handle.xml` | Bottom sheet drag handle |
| Modify | `app/src/main/res/drawable/nav_item_bg.xml` | Active indicator uses primary color |
| Modify | `app/src/main/res/layout/activity_splash.xml` | Gradient bg, refined text |
| Modify | `app/src/main/res/layout/activity_main.xml` | White toolbar + bottom nav |
| Modify | `app/src/main/res/layout/fragment_home.xml` | Search bar corner radius, remove separator |
| Modify | `app/src/main/res/layout/item_place.xml` | Card corner, elevation, margins |
| Modify | `app/src/main/res/layout/bottom_sheet_place_detail.xml` | Drag handle, spacing, red delete |
| Modify | `app/src/main/res/layout/fragment_about.xml` | Card layout with centered content |
| Modify | `app/src/main/java/com/example/spotlight/fragment/homeFragment.kt` | Update chip color references |

---

### Task 1: Update Color Palette

**Files:**
- Modify: `app/src/main/res/values/colors.xml`

- [ ] **Step 1: Replace colors.xml with new palette**

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Primary palette -->
    <color name="purple_200">#FFBB86FC</color>
    <color name="purple_500">#FF5C6BC0</color>
    <color name="purple_700">#FF3949AB</color>

    <!-- Accent -->
    <color name="primary_light">#FF7986CB</color>

    <!-- Surfaces -->
    <color name="surface">#FFF5F5F5</color>
    <color name="white">#FFFFFFFF</color>
    <color name="black">#FF000000</color>

    <!-- Text -->
    <color name="text_primary">#FF1C1B1F</color>
    <color name="text_secondary">#FF757575</color>
    <color name="text_hint">#FF9E9E9E</color>

    <!-- UI elements -->
    <color name="divider">#FFE0E0E0</color>
    <color name="error_red">#FFEF5350</color>

    <!-- Splash gradient -->
    <color name="splash_dark">#FF1A237E</color>
    <color name="splash_light">#FF5C6BC0</color>

    <!-- Kept for Compose template compatibility -->
    <color name="teal_200">#FF03DAC5</color>
    <color name="teal_700">#FF018786</color>
</resources>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/values/colors.xml
git commit -m "style: update color palette to soft indigo clean minimalis"
```

---

### Task 2: Update Dimensions

**Files:**
- Modify: `app/src/main/res/values/dimens.xml`

- [ ] **Step 1: Replace dimens.xml with updated spacing**

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Text sizes -->
    <dimen name="text_title">28sp</dimen>
    <dimen name="text_body">14sp</dimen>
    <dimen name="text_caption">12sp</dimen>
    <dimen name="text_item_title">16sp</dimen>

    <!-- Spacing -->
    <dimen name="padding_small">8dp</dimen>
    <dimen name="padding_medium">16dp</dimen>
    <dimen name="padding_large">24dp</dimen>
    <dimen name="padding_xlarge">20dp</dimen>
    <dimen name="margin_top_xsmall">2dp</dimen>
    <dimen name="margin_top_small">4dp</dimen>
    <dimen name="margin_top_medium">8dp</dimen>
    <dimen name="margin_top_large">24dp</dimen>

    <!-- Card -->
    <dimen name="card_elevation">2dp</dimen>
    <dimen name="card_margin_horizontal">12dp</dimen>
    <dimen name="card_margin_vertical">8dp</dimen>
    <dimen name="card_corner_radius">16dp</dimen>

    <!-- Image -->
    <dimen name="image_height">200dp</dimen>

    <!-- Bottom sheet -->
    <dimen name="bottom_sheet_corner">24dp</dimen>
    <dimen name="drag_handle_width">40dp</dimen>
    <dimen name="drag_handle_height">4dp</dimen>

    <!-- Search -->
    <dimen name="search_corner_radius">28dp</dimen>

    <!-- Chip -->
    <dimen name="chip_corner_radius">20dp</dimen>

    <!-- Toolbar -->
    <dimen name="toolbar_elevation">2dp</dimen>
</resources>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/values/dimens.xml
git commit -m "style: update dimensions for clean minimalis spacing"
```

---

### Task 3: Update Theme & Toolbar Style

**Files:**
- Modify: `app/src/main/res/values/themes.xml`

- [ ] **Step 1: Replace themes.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.SpotLight" parent="Theme.Material3.Light.NoActionBar">
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorOnPrimary">@color/white</item>
        <item name="colorPrimaryDark">@color/purple_700</item>
        <item name="android:statusBarColor">@color/white</item>
        <item name="android:windowLightStatusBar">true</item>
    </style>

    <style name="BottomNavNoIndicator" parent="Widget.Material3.BottomNavigationView">
        <item name="materialThemeOverlay">@style/BottomNavNoIndicatorOverlay</item>
    </style>

    <style name="BottomNavNoIndicatorOverlay">
        <item name="colorSecondaryContainer">@android:color/transparent</item>
        <item name="colorOnSecondaryContainer">@android:color/transparent</item>
    </style>
</resources>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/values/themes.xml
git commit -m "style: white toolbar and status bar for clean theme"
```

---

### Task 4: Create New Drawables

**Files:**
- Create: `app/src/main/res/drawable/bg_splash_gradient.xml`
- Create: `app/src/main/res/drawable/bg_drag_handle.xml`
- Modify: `app/src/main/res/drawable/nav_item_bg.xml`

- [ ] **Step 1: Create splash gradient drawable**

Create `app/src/main/res/drawable/bg_splash_gradient.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <gradient
        android:angle="135"
        android:startColor="@color/splash_dark"
        android:endColor="@color/splash_light"
        android:type="linear" />
</shape>
```

- [ ] **Step 2: Create drag handle drawable**

Create `app/src/main/res/drawable/bg_drag_handle.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/divider" />
    <corners android:radius="2dp" />
</shape>
```

- [ ] **Step 3: Update nav active indicator to use primary color**

Replace `app/src/main/res/drawable/nav_item_bg.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_checked="true">
        <layer-list>
            <item
                android:top="0dp"
                android:bottom="16dp"
                android:gravity="center_horizontal|bottom"
                android:width="48dp"
                android:height="3dp">
                <shape android:shape="rectangle">
                    <solid android:color="@color/purple_500" />
                    <corners android:radius="2dp" />
                </shape>
            </item>
        </layer-list>
    </item>
</selector>
```

- [ ] **Step 4: Commit**

```bash
git add app/src/main/res/drawable/bg_splash_gradient.xml
git add app/src/main/res/drawable/bg_drag_handle.xml
git add app/src/main/res/drawable/nav_item_bg.xml
git commit -m "style: add gradient, drag handle drawables; update nav indicator color"
```

---

### Task 5: Redesign Splash Screen Layout

**Files:**
- Modify: `app/src/main/res/layout/activity_splash.xml`

- [ ] **Step 1: Replace activity_splash.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical"
    android:background="@drawable/bg_splash_gradient">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="SpotLight"
        android:textSize="36sp"
        android:textStyle="bold"
        android:textColor="@color/white"
        android:letterSpacing="0.02" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Temukan tempat terbaik di sekitarmu"
        android:textSize="14sp"
        android:textColor="#B3FFFFFF"
        android:layout_marginTop="8dp" />

</LinearLayout>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/layout/activity_splash.xml
git commit -m "style: splash screen with indigo gradient background"
```

---

### Task 6: Update Main Activity (Toolbar + Bottom Nav)

**Files:**
- Modify: `app/src/main/res/layout/activity_main.xml`

- [ ] **Step 1: Replace activity_main.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:fitsSystemWindows="true">

    <com.google.android.material.appbar.MaterialToolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="@color/white"
        android:elevation="@dimen/toolbar_elevation"
        app:title="SpotLight"
        app:titleTextColor="@color/purple_500"
        app:titleTextAppearance="@style/TextAppearance.MaterialComponents.Headline6"
        app:popupTheme="@style/ThemeOverlay.Material3.Light" />

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/navHostFragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:navGraph="@navigation/nav_graph"
        app:defaultNavHost="true" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNav"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:background="@color/white"
        android:elevation="@dimen/toolbar_elevation"
        app:labelVisibilityMode="labeled"
        app:itemIconTint="@color/nav_item_color"
        app:itemTextColor="@color/nav_item_color"
        app:itemActiveIndicatorStyle="@style/BottomNavNoIndicator"
        app:itemRippleColor="@android:color/transparent"
        app:itemBackground="@drawable/nav_item_bg"
        app:menu="@menu/bottom_nav_menu" />

</LinearLayout>
```

- [ ] **Step 2: Create nav item color selector**

Create `app/src/main/res/color/nav_item_color.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_checked="true" android:color="@color/purple_500" />
    <item android:color="@color/text_hint" />
</selector>
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/res/layout/activity_main.xml
git add app/src/main/res/color/nav_item_color.xml
git commit -m "style: white toolbar and bottom nav with indigo accents"
```

---

### Task 7: Update Home Fragment Layout

**Files:**
- Modify: `app/src/main/res/layout/fragment_home.xml`

- [ ] **Step 1: Replace fragment_home.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/surface">

    <!-- Search bar -->
    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/searchInput"
        style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="@dimen/padding_medium"
        android:layout_marginEnd="@dimen/padding_medium"
        android:layout_marginTop="@dimen/padding_medium"
        app:startIconDrawable="@android:drawable/ic_menu_search"
        app:startIconTint="@color/text_hint"
        app:boxCornerRadiusTopStart="@dimen/search_corner_radius"
        app:boxCornerRadiusTopEnd="@dimen/search_corner_radius"
        app:boxCornerRadiusBottomStart="@dimen/search_corner_radius"
        app:boxCornerRadiusBottomEnd="@dimen/search_corner_radius"
        app:boxStrokeColor="@color/divider"
        app:hintEnabled="false">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/searchEditText"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Cari tempat..."
            android:inputType="text"
            android:singleLine="true" />

    </com.google.android.material.textfield.TextInputLayout>

    <!-- Filter row: chips + sort -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center_vertical"
        android:paddingStart="@dimen/padding_medium"
        android:paddingEnd="@dimen/padding_medium"
        android:paddingTop="@dimen/padding_small"
        android:paddingBottom="@dimen/padding_small">

        <HorizontalScrollView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:scrollbars="none">

            <com.google.android.material.chip.ChipGroup
                android:id="@+id/chipGroupCategory"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:singleSelection="true"
                app:chipSpacingHorizontal="8dp" />

        </HorizontalScrollView>

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnSort"
            style="@style/Widget.Material3.Button.TextButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text=""
            app:icon="@drawable/ic_sort"
            app:iconTint="@color/purple_500"
            app:iconGravity="textStart"
            android:textSize="12sp"
            android:minWidth="0dp"
            android:paddingStart="8dp"
            android:paddingEnd="8dp" />

    </LinearLayout>

    <!-- RecyclerView -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvPlaces"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:clipToPadding="false"
        android:paddingStart="@dimen/card_margin_horizontal"
        android:paddingEnd="@dimen/card_margin_horizontal"
        android:paddingTop="@dimen/padding_small"
        android:paddingBottom="@dimen/padding_small" />

</LinearLayout>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/layout/fragment_home.xml
git commit -m "style: home screen with rounded search, surface bg, no separator"
```

---

### Task 8: Update Card Item Layout

**Files:**
- Modify: `app/src/main/res/layout/item_place.xml`

- [ ] **Step 1: Replace item_place.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginStart="@dimen/card_margin_horizontal"
    android:layout_marginEnd="@dimen/card_margin_horizontal"
    android:layout_marginTop="@dimen/card_margin_vertical"
    android:layout_marginBottom="@dimen/card_margin_vertical"
    app:cardCornerRadius="@dimen/card_corner_radius"
    app:cardElevation="@dimen/card_elevation"
    app:cardBackgroundColor="@color/white">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/imgPlace"
            android:layout_width="match_parent"
            android:layout_height="@dimen/image_height"
            android:scaleType="centerCrop" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="@dimen/padding_medium">

            <TextView
                android:id="@+id/tvName"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="@dimen/text_item_title"
                android:textStyle="bold"
                android:textColor="@color/text_primary" />

            <TextView
                android:id="@+id/tvCategory"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="@dimen/text_caption"
                android:textColor="@color/primary_light"
                android:layout_marginTop="@dimen/margin_top_small" />

            <TextView
                android:id="@+id/tvLocation"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="@dimen/text_caption"
                android:textColor="@color/text_secondary"
                android:layout_marginTop="@dimen/margin_top_xsmall" />

            <TextView
                android:id="@+id/tvRating"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="@dimen/text_caption"
                android:textColor="@color/text_secondary"
                android:layout_marginTop="@dimen/margin_top_xsmall" />

        </LinearLayout>

    </LinearLayout>

</com.google.android.material.card.MaterialCardView>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/layout/item_place.xml
git commit -m "style: card item with larger corners, softer shadow, accent category"
```

---

### Task 9: Update Bottom Sheet Detail Layout

**Files:**
- Modify: `app/src/main/res/layout/bottom_sheet_place_detail.xml`

- [ ] **Step 1: Replace bottom_sheet_place_detail.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <!-- Drag handle -->
    <View
        android:layout_width="@dimen/drag_handle_width"
        android:layout_height="@dimen/drag_handle_height"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="12dp"
        android:layout_marginBottom="8dp"
        android:background="@drawable/bg_drag_handle" />

    <ImageView
        android:id="@+id/imgPlace"
        android:layout_width="match_parent"
        android:layout_height="@dimen/image_height"
        android:scaleType="centerCrop" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="@dimen/padding_xlarge">

        <TextView
            android:id="@+id/tvName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="@dimen/text_item_title"
            android:textStyle="bold"
            android:textColor="@color/text_primary" />

        <TextView
            android:id="@+id/tvCategory"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/margin_top_small"
            android:textSize="@dimen/text_caption"
            android:textColor="@color/primary_light" />

        <TextView
            android:id="@+id/tvLocation"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/margin_top_xsmall"
            android:textSize="@dimen/text_caption"
            android:textColor="@color/text_secondary" />

        <TextView
            android:id="@+id/tvRating"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/margin_top_xsmall"
            android:textSize="@dimen/text_caption"
            android:textColor="@color/text_secondary" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnMaps"
            style="@style/Widget.Material3.Button.TonalButton"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/padding_medium"
            android:text="Buka di Google Maps" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:layout_marginTop="12dp">

            <com.google.android.material.button.MaterialButton
                android:id="@+id/btnFavorite"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:layout_marginEnd="@dimen/padding_small"
                android:text="Tambah Favorit" />

            <com.google.android.material.button.MaterialButton
                android:id="@+id/btnDelete"
                style="@style/Widget.Material3.Button.OutlinedButton"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Hapus"
                android:textColor="@color/error_red"
                app:strokeColor="@color/error_red" />

        </LinearLayout>

    </LinearLayout>

</LinearLayout>
```

- [ ] **Step 2: Commit**

```bash
git add app/src/main/res/layout/bottom_sheet_place_detail.xml
git commit -m "style: bottom sheet with drag handle, spacing, red delete button"
```

---

### Task 10: Update About Page Layout

**Files:**
- Modify: `app/src/main/res/layout/fragment_about.xml`

- [ ] **Step 1: Replace fragment_about.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical"
    android:background="@color/surface"
    android:padding="@dimen/padding_large">

    <com.google.android.material.card.MaterialCardView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:cardCornerRadius="@dimen/card_corner_radius"
        app:cardElevation="1dp"
        app:cardBackgroundColor="@color/white"
        app:cardUseCompatPadding="true">

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:gravity="center"
            android:padding="@dimen/padding_large"
            android:minWidth="260dp">

            <!-- App initial -->
            <TextView
                android:layout_width="64dp"
                android:layout_height="64dp"
                android:gravity="center"
                android:background="@drawable/bg_circle_initial"
                android:text="S"
                android:textSize="28sp"
                android:textStyle="bold"
                android:textColor="@color/white" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="@dimen/padding_medium"
                android:text="SpotLight"
                android:textSize="24sp"
                android:textStyle="bold"
                android:textColor="@color/purple_500" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="@dimen/margin_top_small"
                android:text="Aplikasi rekomendasi tempat hits\ndi Jabodetabek"
                android:textSize="@dimen/text_body"
                android:textColor="@color/text_secondary"
                android:gravity="center" />

            <View
                android:layout_width="48dp"
                android:layout_height="1dp"
                android:layout_marginTop="@dimen/padding_medium"
                android:layout_marginBottom="@dimen/padding_medium"
                android:background="@color/divider" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="v1.0.0"
                android:textSize="@dimen/text_caption"
                android:textColor="@color/text_hint" />

        </LinearLayout>

    </com.google.android.material.card.MaterialCardView>

</LinearLayout>
```

- [ ] **Step 2: Create circle initial background drawable**

Create `app/src/main/res/drawable/bg_circle_initial.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">
    <solid android:color="@color/purple_500" />
</shape>
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/res/layout/fragment_about.xml
git add app/src/main/res/drawable/bg_circle_initial.xml
git commit -m "style: about page with centered card and circle initial"
```

---

### Task 11: Update Chip Colors in HomeFragment Kotlin

**Files:**
- Modify: `app/src/main/java/com/example/spotlight/fragment/homeFragment.kt`

- [ ] **Step 1: Update chip color references in setupChips()**

In `app/src/main/java/com/example/spotlight/fragment/homeFragment.kt`, replace the `setupChips()` method. The change is in the color references — replace `R.color.purple_500`, `R.color.white`, `R.color.black` with the new color resources.

Replace the entire `setupChips()` method (lines 50-83) with:

```kotlin
private fun setupChips() {
    val categories = listOf("Semua" to null) +
        PlaceCategory.entries.map { it.name to it.name }

    val primary = resources.getColor(R.color.purple_500, null)
    val white = resources.getColor(R.color.white, null)
    val secondaryText = resources.getColor(R.color.text_secondary, null)

    val chipBgColor = ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
        intArrayOf(primary, white)
    )
    val chipTextColor = ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
        intArrayOf(white, secondaryText)
    )

    categories.forEach { (label, category) ->
        val chip = Chip(requireContext()).apply {
            text = label
            isClickable = true
            isCheckable = true
            chipBackgroundColor = chipBgColor
            setTextColor(chipTextColor)
            setOnClickListener {
                currentCategory = category
                loadData()
            }
        }
        binding.chipGroupCategory.addView(chip)
    }

    binding.chipGroupCategory.getChildAt(0)?.performClick()
}
```

Changes from original:
- `val black` renamed to `val secondaryText` using `R.color.text_secondary` instead of `R.color.black`
- Chip unchecked text now uses secondary gray instead of pure black — softer feel

- [ ] **Step 2: Commit**

```bash
git add app/src/main/java/com/example/spotlight/fragment/homeFragment.kt
git commit -m "style: update chip text color to soft gray for clean minimalis"
```

---

### Task 12: Build & Visual Verification

- [ ] **Step 1: Build the project**

```bash
./gradlew assembleDebug
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 2: Visual check — run on device/emulator and verify all screens**

Checklist:
- [ ] Splash: gradient background, clean text
- [ ] Home: white toolbar, rounded search, soft chips, clean cards
- [ ] Card item: larger corners, softer shadow, purple category text
- [ ] Bottom sheet: drag handle, red delete button, spacing
- [ ] About: centered card with circle initial
- [ ] Bottom nav: white bg, indigo active, gray inactive
- [ ] No hardcoded purple left visible (all using new palette)

- [ ] **Step 3: Final commit if any fixes needed**

```bash
git add -A
git commit -m "fix: visual adjustments after build verification"
```
