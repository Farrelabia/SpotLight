# Settings & Favorite Design

## Overview
Perubahan pada Option Menu (titik tiga), tombol Favorite, dan penghapusan tombol Hapus di bottom sheet.

## 1. Option Menu → Dark Mode Dialog

**File yang diubah:** `MainActivity.kt`, `option_menu.xml`

- Option menu hanya berisi 1 item: "Settings"
- Klik Settings → tampilkan MaterialAlertDialog berisi SwitchMaterial untuk Dark Mode toggle
- Toggle state disimpan ke SharedPreferences (`dark_mode` boolean)
- Dialog punya tombol "Tutup"
- Dark mode mengubah theme antara `Theme.SpotLight` dan `Theme.SpotLight.Dark` via `AppCompatDelegate.setDefaultNightMode()`

### Alur:
1. User klik titik tiga → pilih "Settings"
2. Muncul dialog dengan judul "Pengaturan", berisi SwitchMaterial "Dark Mode"
3. Toggle on → `MODE_NIGHT_YES`, off → `MODE_NIGHT_NO`
4. Klik "Tutup" → dialog close

## 2. Favorite Button → Dialog + Snackbar

**File yang diubah:** `PlaceDetailBottomSheet.kt`, `bottom_sheet_place_detail.xml`

- Tombol Favorite jadi full-width (karena tombol Hapus dihapus)
- Tambah icon love di tombol
- Klik → MaterialAlertDialog konfirmasi
- "Ya" → Snackbar "Ditambahkan ke favorit"
- "Batal" → dismiss dialog
- Tidak ada state toggle / persistensi — visual only

### Alur:
1. User klik "Tambah Favorit" di bottom sheet
2. Dialog muncul: "Tambah Favorit" / "Tambahkan tempat ini ke daftar favorit?"
3. User klik "Ya" → Snackbar "Ditambahkan ke favorit" muncul
4. User klik "Batal" → dialog dismiss tanpa aksi

## 3. Tombol Hapus → Dihapus

**File yang diubah:** `bottom_sheet_place_detail.xml`, `PlaceDetailBottomSheet.kt`

- `btnDelete` dihapus dari layout XML
- Handler `btnDelete` dihapus dari Kotlin
- `btnFavorite` jadi full-width (hapus `layout_weight`, `layout_marginEnd`)

## PDF Requirements Checklist

| Requirement | Dipenuhi oleh |
|------------|---------------|
| Option Menu | Settings di titik tiga |
| MaterialAlertDialog | Dialog Dark Mode + Dialog Favorite |
| Snackbar | Feedback setelah tambah favorit |
| Bottom Sheet | Sudah ada (tidak diubah) |

## Files Changed

1. `app/src/main/res/menu/option_menu.xml` — menu item Settings
2. `app/src/main/java/com/example/spotlight/MainActivity.kt` — handler Settings → dialog Dark Mode
3. `app/src/main/res/layout/bottom_sheet_place_detail.xml` — hapus btnDelete, btnFavorite full-width
4. `app/src/main/java/com/example/spotlight/PlaceDetailBottomSheet.kt` — favorite dialog + snackbar, hapus handler delete
5. `app/src/main/res/values/themes.xml` — tambah dark theme (jika belum ada)
