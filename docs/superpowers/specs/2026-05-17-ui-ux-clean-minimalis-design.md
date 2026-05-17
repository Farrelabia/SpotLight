# SpotLight UI/UX Redesign — Clean Minimalis (Soft Touch)

**Date**: 2026-05-17
**Approach**: Soft Touch — polish existing layouts without restructuring
**Style**: Clean Minimalis — whitespace, rounded corners, soft purple palette

## Color Palette

| Name | Hex | Usage |
|---|---|---|
| Primary | `#5C6BC0` | Toolbar accents, active states, buttons |
| Primary Dark | `#3949AB` | Status bar, gradient dark end |
| Primary Light | `#7986CB` | Chip active bg, category text accent |
| Surface | `#F5F5F5` | Page backgrounds (off-white) |
| White | `#FFFFFF` | Cards, toolbar, bottom nav backgrounds |
| On Surface | `#1C1B1F` | Primary text |
| Secondary Text | `#757575` | Captions, location, rating text |
| Hint/Disabled | `#9E9E9E` | Inactive nav icons, placeholder |
| Divider | `#E0E0E0` | Search border, drag handle, dividers |
| Error | `#EF5350` | Destructive action button (hapus) |
| Splash Dark | `#1A237E` | Splash gradient top |
| Splash Light | `#5C6BC0` | Splash gradient bottom |

## Splash Screen

- Background: diagonal gradient `#1A237E` (top) to `#5C6BC0` (bottom)
- "SpotLight" centered, 36sp bold, white, no emoji
- Tagline "Temukan tempat terbaik di sekitarmu", 14sp, white 70% opacity
- Duration: 2 seconds, then transition to MainActivity

## Home Screen

- Page background: `#F5F5F5`
- Search bar: TextInputLayout OutlinedBox, corner radius 28dp, border `#E0E0E0`, search icon gray
- Chips: transparent when inactive with gray border, `#5C6BC0` bg + white text when active, 12sp font, 20dp corner
- Sort button: icon-only, primary color, no separator line
- RecyclerView padding: 12dp horizontal, 8dp vertical

## Card Item (item_place.xml)

- Corner radius: 16dp (up from 12dp)
- Elevation: 2dp (down from 4dp)
- Margin: 12dp horizontal, 8dp vertical
- Image height: 200dp (up from 180dp), centerCrop
- Text padding: 16dp
- Name: 16sp bold, `#1C1B1F`
- Category: 12sp, `#7986CB` (purple accent instead of gray)
- Location: 12sp, `#757575`
- Rating: 12sp, `#757575`

## Detail Bottom Sheet

- Top corner radius: 24dp
- Drag handle at top (pill shape, `#E0E0E0`)
- Image with rounded top corners matching sheet
- Text area padding: 20dp
- "Buka di Google Maps" button: tonal, primary color
- "Tambah Favorit" button: filled, primary color
- "Hapus" button: outlined, `#EF5350` (red for destructive)
- Button spacing: 12dp

## About Page

- Background: `#FFFFFF`
- Centered MaterialCardView (corner 16dp, elevation 1dp) containing:
  - App icon or "S" initial in circle
  - "SpotLight" — 24sp bold, primary color
  - Tagline — 14sp, gray
  - Thin divider
  - Version "v1.0.0" — 12sp, light gray
- Card padding: 24dp

## Toolbar

- Background: `#FFFFFF` (was `purple_500`)
- Title "SpotLight": `#5C6BC0`, 18sp bold (no emoji)
- Elevation: 2dp

## Bottom Navigation

- Background: `#FFFFFF` (was `purple_500`)
- Inactive icon/label: `#9E9E9E`
- Active icon/label: `#5C6BC0`
- No purple background

## Files to Modify

1. `app/src/main/res/values/colors.xml` — new color palette
2. `app/src/main/res/values/dimens.xml` — updated spacing values
3. `app/src/main/res/values/themes.xml` — toolbar style, status bar color
4. `app/src/main/res/drawable/splash_gradient.xml` — new gradient drawable (new file)
5. `app/src/main/res/layout/activity_splash.xml` — gradient bg, refined text
6. `app/src/main/res/layout/activity_main.xml` — white toolbar + bottom nav
7. `app/src/main/res/layout/fragment_home.xml` — search bar styling, chip colors
8. `app/src/main/res/layout/item_place.xml` — card refinement
9. `app/src/main/res/layout/bottom_sheet_place_detail.xml` — drag handle, spacing, red delete button
10. `app/src/main/res/layout/fragment_about.xml` — card layout
11. `app/src/main/java/com/example/spotlight/fragment/homeFragment.kt` — update chip color references
