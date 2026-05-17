# SpotLight Final Project Design

## Overview
Penyempurnaan aplikasi SpotLight dengan fitur navigasi tingkat lanjut dan Material Design 3 components sesuai requirements Final Project Study Club Android Beginner UPNVJ 2026.

## 1. Tab Layout + ViewPager2 (HomeFragment)

**Perubahan:** HomeFragment sekarang berisi TabLayout + ViewPager2 di atasnya.

- **5 tab:** Semua, Cafe, Museum, Kuliner, Taman
- **1 reusable fragment:** `PlaceListFragment` menerima argumen kategori (String), menampilkan RecyclerView yang di-filter dan di-sort by rating descending
- **Tab "Semua"** menampilkan semua tempat tanpa filter
- **ViewPager2** menggunakan FragmentStateAdapter
- **Filter button** di atas tab/layout untuk sort tambahan (by rating/name)

**Data flow:**
- `HomeFragment` membuat list `Place` hardcoded (dipindah dari HomeFragment saat ini)
- ViewPager2 + TabLayout mengatur tab switching
- Setiap `PlaceListFragment` instance menerima category filter via Bundle/Safe Args
- RecyclerView + PlaceAdapter menampilkan filtered + sorted list

## 2. TopAppBar MD3

**Perubahan:** Ganti Toolbar biasa di `activity_main.xml` jadi Material 3 `CenterAligned` TopAppBar.

- Gunakan `com.google.android.material.appbar.MaterialToolbar`
- Title: "SpotLight"
- Background: colorPrimary
- TitleTextColor: white
- Support ActionBar di MainActivity tetap menggunakan setSupportActionBar

## 3. Option Menu

**Perubahan:** Tambah option menu di TopAppBar.

- **Ikon:** titik tiga (overflow menu)
- **Isi:** cuma "Settings" (placeholder, bikin Toast "Settings coming soon")
- Override `onCreateOptionsMenu` di MainActivity
- Buat file `res/menu/option_menu.xml`

## 4. Bottom Sheet (Detail Place)

**Perubahan:** Tap item place sekarang membuka Modal Bottom Sheet, bukan Toast.

- **Modal Bottom Sheet** menampilkan detail tempat: nama, kategori, lokasi, rating, gambar
- **Tombol "Tambah ke Favorit":** klik -> Snackbar muncul "Ditambahkan ke favorit"
- **Tombol "Hapus":** klik -> MaterialAlertDialog konfirmasi "Yakin mau hapus tempat ini?" dengan tombol Ya/Tidak
- Bottom Sheet layout: `fragment_place_detail.xml`
- Bottom Sheet class: `PlaceDetailBottomSheet` extends `BottomSheetDialogFragment`
- Data Place dikirim via Safe Args/Bundle

## 5. Safe Args

- Tambah plugin `androidx.navigation.safeargs` di build.gradle.kts
- Definisikan arguments di `nav_graph.xml` jika perlu
- Digunakan untuk mengirim data Place ke Bottom Sheet/detail

## Files to Create/Modify

### New files:
- `PlaceListFragment.kt` — reusable fragment untuk tab content
- `PlaceDetailBottomSheet.kt` — modal bottom sheet detail
- `fragment_place_list.xml` — layout untuk PlaceListFragment (RecyclerView)
- `fragment_place_detail.xml` — layout untuk bottom sheet detail
- `option_menu.xml` — option menu dengan Settings
- `fragment_home.xml` — update dengan TabLayout + ViewPager2

### Modified files:
- `activity_main.xml` — ganti Toolbar ke Material 3 TopAppBar
- `MainActivity.kt` — setup option menu, inflate option_menu
- `HomeFragment.kt` — setup TabLayout + ViewPager2 + filter
- `nav_graph.xml` — tambah destination jika perlu
- `HomeFragment` — pindahkan hardcoded data ke companion object atau ViewModel sederhana
- `build.gradle.kts (app)` — tambah dependency Safe Args, Material 3
- `dimens.xml`, `colors.xml` — update jika perlu

## Out of Scope
- Navigation Drawer (skip, app belum punya 5+ destinasi)
- Favorit persistence (hanya Snackbar feedback, tanpa database)
- Settings page (hanya placeholder Toast)
