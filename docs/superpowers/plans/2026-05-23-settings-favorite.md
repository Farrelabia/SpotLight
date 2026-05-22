# Settings & Favorite Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add Dark Mode toggle via option menu dialog, change favorite button to show dialog + snackbar, remove delete button.

**Architecture:** Dark mode uses AppCompatDelegate night mode with SharedPreferences for persistence. Favorite button replaces the snackbar-only behavior with a confirmation dialog first, then snackbar. No database changes needed.

**Tech Stack:** Android Kotlin, Material Design 3, SharedPreferences, AppCompatDelegate

---

### Task 1: Dark Mode Theme Resources

**Files:**
- Create: `app/src/main/res/values-night/colors.xml`
- Modify: `app/src/main/res/values/themes.xml`

- [ ] **Step 1: Create night mode colors**

Create `app/src/main/res/values-night/colors.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="surface">#FF1E1B2E</color>
    <color name="white">#FF2D2B3D</color>
    <color name="text_primary">#FFE8E5EE</color>
    <color name="text_secondary">#FFB0ACC0</color>
    <color name="text_hint">#FF6E6A7C</color>
    <color name="divider">#FF3D3A4E</color>
</resources>
```

- [ ] **Step 2: Add dark theme to themes.xml**

Append inside `<resources>` in `app/src/main/res/values/themes.xml`:

```xml
    <style name="Theme.SpotLight.Dark" parent="Theme.Material3.Dark.NoActionBar">
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorOnPrimary">@color/white</item>
        <item name="android:statusBarColor">@color/surface</item>
    </style>
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/res/values-night/colors.xml app/src/main/res/values/themes.xml
git commit -m "feat: add dark mode theme resources"
```

---

### Task 2: Dark Mode Dialog in Settings

**Files:**
- Modify: `app/src/main/java/com/example/spotlight/MainActivity.kt`

- [ ] **Step 1: Replace Toast with Dark Mode dialog**

In `MainActivity.kt`, replace the `onOptionsItemSelected` method and add imports. Replace the entire `onOptionsItemSelected` method:

```kotlin
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                showDarkModeDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDarkModeDialog() {
        val sharedPreferences = getSharedPreferences("spotlight_prefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        val switch = com.google.android.material.materialswitch.MaterialSwitch(this).apply {
            isChecked = isDarkMode
            text = "Dark Mode"
            setPadding(48, 24, 48, 24)
        }

        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("Pengaturan")
            .setView(switch)
            .setPositiveButton("Tutup") { dialog, _ ->
                sharedPreferences.edit().putBoolean("dark_mode", switch.isChecked).apply()
                val mode = if (switch.isChecked)
                    androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
                else
                    androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(mode)
                dialog.dismiss()
            }
            .show()
    }
```

- [ ] **Step 2: Apply saved dark mode on startup**

Add at the end of `onCreate` in `MainActivity.kt`, before the closing `}`:

```kotlin
        val sharedPreferences = getSharedPreferences("spotlight_prefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        val mode = if (isDarkMode)
            androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
        else
            androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(mode)
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/example/spotlight/MainActivity.kt
git commit -m "feat: add dark mode toggle dialog in settings menu"
```

---

### Task 3: Remove Delete Button & Update Favorite Layout

**Files:**
- Modify: `app/src/main/res/layout/bottom_sheet_place_detail.xml`

- [ ] **Step 1: Remove btnDelete and update btnFavorite layout**

Replace the bottom LinearLayout section (lines 82-106) in `bottom_sheet_place_detail.xml`. Replace the entire second LinearLayout (containing btnFavorite and btnDelete) with just:

```xml
        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnFavorite"
            style="@style/Widget.Material3.Button.TonalButton"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="12dp"
            app:icon="@drawable/ic_favorite_border"
            app:iconGravity="textStart"
            android:text="Tambah Favorit" />
```

- [ ] **Step 2: Create favorite border icon**

Create `app/src/main/res/drawable/ic_favorite_border.xml`:

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="?attr/colorPrimary">
    <path
        android:fillColor="@android:color/white"
        android:pathData="M16.5,3c-1.74,0 -3.41,0.81 -4.5,2.09C10.91,3.81 9.24,3 7.5,3 4.42,3 2,5.42 2,8.5c0,3.78 3.4,6.86 8.55,11.54L12,21.35l1.45,-1.32C18.6,15.36 22,12.28 22,8.5 22,5.42 19.58,3 16.5,3zM12.1,18.55l-0.1,0.1 -0.1,-0.1C7.14,14.24 4,11.39 4,8.5 4,6.5 5.5,5 7.5,5c1.54,0 3.04,0.99 3.57,2.36h1.87C13.46,5.99 14.96,5 16.5,5c2,0 3.5,1.5 3.5,3.5 0,2.89 -3.14,5.74 -7.9,10.05z"/>
</vector>
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/res/layout/bottom_sheet_place_detail.xml app/src/main/res/drawable/ic_favorite_border.xml
git commit -m "feat: remove delete button, update favorite button layout"
```

---

### Task 4: Favorite Dialog + Snackbar

**Files:**
- Modify: `app/src/main/java/com/example/spotlight/PlaceDetailBottomSheet.kt`

- [ ] **Step 1: Replace favorite handler with dialog**

In `PlaceDetailBottomSheet.kt`, replace the `btnFavorite.setOnClickListener` block (lines 45-47) with:

```kotlin
        binding.btnFavorite.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Tambah Favorit")
                .setMessage("Tambahkan tempat ini ke daftar favorit?")
                .setPositiveButton("Ya") { dialog, _ ->
                    Snackbar.make(view, "Ditambahkan ke favorit", Snackbar.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
```

- [ ] **Step 2: Remove btnDelete handler**

Delete the entire `btnDelete.setOnClickListener` block (lines 49-59):

```kotlin
        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Yakin mau hapus tempat ini?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dismiss()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
```

- [ ] **Step 3: Build and verify**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL, no compilation errors

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/example/spotlight/PlaceDetailBottomSheet.kt
git commit -m "feat: add favorite dialog confirmation with snackbar"
```
