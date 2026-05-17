# SpotLight Final Project Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add Tab Layout + ViewPager2, Option Menu, MD3 TopAppBar, Bottom Sheet, Dialog/Snackbar, and Safe Args to the SpotLight Android app.

**Architecture:** Refactor HomeFragment to use TabLayout + ViewPager2 with 5 category tabs (Semua, Cafe, Museum, Kuliner, Taman). Each tab hosts a reusable PlaceListFragment with a filtered/sorted RecyclerView. Tapping a place opens a Modal Bottom Sheet with Dialog and Snackbar interactions.

**Tech Stack:** Kotlin, Android SDK 36, ViewBinding, Navigation Component, Material Design 3, ViewPager2, Safe Args, Parcelize

---

### Task 1: Update Dependencies

**Files:**
- Modify: `gradle/libs.versions.toml`
- Modify: `build.gradle.kts` (project-level)
- Modify: `app/build.gradle.kts`

- [ ] **Step 1: Add versions and plugins to libs.versions.toml**

Add these entries to the existing file:

```toml
[versions]
# Add after existing versions:
viewpager2 = "1.0.0"
navigationSafeArgs = "2.7.7"

[libraries]
# Add after existing libraries:
androidx-viewpager2 = { group = "androidx.viewpager2", name = "viewpager2", version.ref = "viewpager2" }

[plugins]
# Add after existing plugins:
safe-args = { id = "androidx.navigation.safeargs.kotlin", version.ref = "navigationSafeArgs" }
```

- [ ] **Step 2: Register safe-args plugin in project build.gradle.kts**

```kotlin
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.safe.args) apply false
}
```

- [ ] **Step 3: Apply plugins and add dependency in app/build.gradle.kts**

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.safe.args)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.spotlight"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.spotlight"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation(libs.androidx.viewpager2)
}
```

- [ ] **Step 4: Build and verify**

Run: `./gradlew assembleDebug` from project root.
Expected: BUILD SUCCESSFUL with no errors.

---

### Task 2: Make Place Parcelable + Create DataSource

**Files:**
- Modify: `app/src/main/java/com/example/spotlight/model/Place.kt`
- Create: `app/src/main/java/com/example/spotlight/datasource/DataSource.kt`

- [ ] **Step 1: Update Place.kt with @Parcelize**

```kotlin
package com.example.spotlight.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class PlaceCategory {
    CAFE, MUSEUM, KULINER, TAMAN
}

@Parcelize
data class Place(
    val name: String,
    val category: PlaceCategory,
    val location: String,
    val rating: Float,
    val imageRes: Int
) : Parcelable
```

- [ ] **Step 2: Create DataSource.kt**

```kotlin
package com.example.spotlight.datasource

import com.example.spotlight.R
import com.example.spotlight.model.Place
import com.example.spotlight.model.PlaceCategory

object DataSource {

    private val allPlaces = listOf(
        Place("Kopi Nako Depok", PlaceCategory.CAFE, "Depok", 4.4f, R.drawable.kopi_nako_depok),
        Place("MATCHAMAN, Blok M", PlaceCategory.CAFE, "Jakarta Selatan", 4.7f, R.drawable.matchaman_blok_m),
        Place("Museum MACAN", PlaceCategory.MUSEUM, "Jakarta Barat", 4.8f, R.drawable.museum_macan),
        Place("Museum Nasional", PlaceCategory.MUSEUM, "Jakarta Pusat", 4.6f, R.drawable.museum_nasional),
        Place("Obihiro nikudon", PlaceCategory.KULINER, "Jakarta Selatan", 4.6f, R.drawable.obihiro_nikudon),
        Place("Waduk Brigif", PlaceCategory.TAMAN, "Depok", 4.7f, R.drawable.waduk_brigif),
        Place("Taman Ismail Marzuki", PlaceCategory.TAMAN, "Jakarta Pusat", 4.5f, R.drawable.taman_ismail_marzuki)
    )

    fun getPlaces(category: String? = null, sortBy: String = "rating_desc"): List<Place> {
        var result = if (category != null) {
            allPlaces.filter { it.category.name == category }
        } else {
            allPlaces
        }

        result = when (sortBy) {
            "rating_desc" -> result.sortedByDescending { it.rating }
            "rating_asc" -> result.sortedBy { it.rating }
            "name_asc" -> result.sortedBy { it.name }
            "name_desc" -> result.sortedByDescending { it.name }
            else -> result.sortedByDescending { it.rating }
        }

        return result
    }
}
```

- [ ] **Step 3: Build and verify**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL.

---

### Task 3: TopAppBar MD3 + Option Menu

**Files:**
- Modify: `app/src/main/res/values/themes.xml`
- Modify: `app/src/main/res/layout/activity_main.xml`
- Create: `app/src/main/res/menu/option_menu.xml`
- Modify: `app/src/main/java/com/example/spotlight/MainActivity.kt`

- [ ] **Step 1: Update themes.xml to Material 3**

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.SpotLight" parent="Theme.Material3.Light.NoActionBar">
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorOnPrimary">@color/white</item>
    </style>
</resources>
```

- [ ] **Step 2: Update activity_main.xml with MaterialToolbar**

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
        android:background="@color/purple_500"
        app:title="SpotLight"
        app:titleTextColor="@color/white" />

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
        android:layout_height="56dp"
        android:background="@color/purple_500"
        app:itemIconTint="@color/white"
        app:itemTextColor="@color/white"
        app:menu="@menu/bottom_nav_menu" />

</LinearLayout>
```

- [ ] **Step 3: Create option_menu.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/action_settings"
        android:title="Settings"
        app:showAsAction="never" />
</menu>
```

- [ ] **Step 4: Update MainActivity.kt with option menu**

```kotlin
package com.example.spotlight

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.spotlight.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.spotlight.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Settings coming soon", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
```

- [ ] **Step 5: Build and verify**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL. App shows Material 3 toolbar with three-dot menu showing "Settings".

---

### Task 4: Create PlaceListFragment + Layout

**Files:**
- Create: `app/src/main/res/layout/fragment_place_list.xml`
- Create: `app/src/main/java/com/example/spotlight/fragment/PlaceListFragment.kt`

- [ ] **Step 1: Create fragment_place_list.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvPlaces"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="@dimen/padding_small"
        android:clipToPadding="false" />

</LinearLayout>
```

- [ ] **Step 2: Create PlaceListFragment.kt**

```kotlin
package com.example.spotlight.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotlight.PlaceDetailBottomSheet
import com.example.spotlight.adapter.PlaceAdapter
import com.example.spotlight.databinding.FragmentPlaceListBinding
import com.example.spotlight.datasource.DataSource

class PlaceListFragment : Fragment() {

    private var _binding: FragmentPlaceListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlaceAdapter
    private var category: String? = null
    private var currentSort: String = "rating_desc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(ARG_CATEGORY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlaceAdapter(emptyList()) { place ->
            PlaceDetailBottomSheet.newInstance(place)
                .show(childFragmentManager, "place_detail")
        }

        binding.rvPlaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlaces.adapter = adapter

        loadData()
    }

    private fun loadData() {
        val places = DataSource.getPlaces(category, currentSort)
        adapter.updateData(places)
    }

    fun updateSortOrder(sortBy: String) {
        currentSort = sortBy
        loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_CATEGORY = "arg_category"

        fun newInstance(category: String?): PlaceListFragment {
            return PlaceListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
        }
    }
}
```

- [ ] **Step 3: Build and verify**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL. PlaceListFragment compiles (note: PlaceAdapter needs update in Task 7, so there may be a compile error here that resolves after Task 7).

---

### Task 5: Refactor HomeFragment with TabLayout + ViewPager2

**Files:**
- Create: `app/src/main/java/com/example/spotlight/adapter/PlacePagerAdapter.kt`
- Modify: `app/src/main/res/layout/fragment_home.xml`
- Modify: `app/src/main/java/com/example/spotlight/fragment/homeFragment.kt`

- [ ] **Step 1: Create PlacePagerAdapter.kt**

```kotlin
package com.example.spotlight.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.spotlight.fragment.PlaceListFragment
import com.example.spotlight.model.PlaceCategory

class PlacePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val categories = listOf<Pair<String, PlaceCategory?>>(
        "Semua" to null,
        "Cafe" to PlaceCategory.CAFE,
        "Museum" to PlaceCategory.MUSEUM,
        "Kuliner" to PlaceCategory.KULINER,
        "Taman" to PlaceCategory.TAMAN
    )

    private val fragments = mutableMapOf<Int, PlaceListFragment>()

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val (_, category) = categories[position]
        val fragment = PlaceListFragment.newInstance(category?.name)
        fragments[position] = fragment
        return fragment
    }

    fun getPageTitle(position: Int): String = categories[position].first

    fun notifySortChanged(sortBy: String) {
        fragments.values.forEach { it.updateSortOrder(sortBy) }
    }
}
```

- [ ] **Step 2: Update fragment_home.xml**

Replace the entire file with:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- Sort row -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="end|center_vertical"
        android:paddingStart="@dimen/padding_medium"
        android:paddingEnd="@dimen/padding_medium"
        android:paddingTop="@dimen/padding_small"
        android:paddingBottom="@dimen/margin_top_small">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Urutkan:"
            android:textSize="@dimen/text_caption" />

        <ImageButton
            android:id="@+id/btnSort"
            android:layout_width="32dp"
            android:layout_height="32dp"
            android:layout_marginStart="@dimen/margin_top_small"
            android:src="@android:drawable/ic_menu_sort_by_size"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="Sort" />

    </LinearLayout>

    <!-- Tab Layout -->
    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabMode="scrollable"
        app:tabIndicatorColor="@color/purple_500"
        app:tabSelectedTextColor="@color/purple_500" />

    <!-- ViewPager2 -->
    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

</LinearLayout>
```

- [ ] **Step 3: Rewrite homeFragment.kt**

```kotlin
package com.example.spotlight.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.spotlight.adapter.PlacePagerAdapter
import com.example.spotlight.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagerAdapter: PlacePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = PlacePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = 4

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pagerAdapter.getPageTitle(position)
        }.attach()

        binding.btnSort.setOnClickListener {
            showSortMenu(it)
        }
    }

    private fun showSortMenu(view: View) {
        PopupMenu(requireContext(), view).apply {
            menu.add(0, 0, 0, "Rating Tertinggi")
            menu.add(0, 1, 1, "Rating Terendah")
            menu.add(0, 2, 2, "Nama A-Z")
            menu.add(0, 3, 3, "Nama Z-A")
            setOnMenuItemClickListener { item ->
                val sortBy = when (item.itemId) {
                    0 -> "rating_desc"
                    1 -> "rating_asc"
                    2 -> "name_asc"
                    3 -> "name_desc"
                    else -> "rating_desc"
                }
                pagerAdapter.notifySortChanged(sortBy)
                true
            }
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

- [ ] **Step 4: Build and verify**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL. Home screen shows 5 tabs (Semua, Cafe, Museum, Kuliner, Taman) with sort button.

---

### Task 6: Bottom Sheet + Dialog + Snackbar

**Files:**
- Create: `app/src/main/res/layout/bottom_sheet_place_detail.xml`
- Create: `app/src/main/java/com/example/spotlight/PlaceDetailBottomSheet.kt`

- [ ] **Step 1: Create bottom_sheet_place_detail.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="@dimen/padding_medium">

    <ImageView
        android:id="@+id/imgPlace"
        android:layout_width="match_parent"
        android:layout_height="@dimen/image_height"
        android:scaleType="centerCrop" />

    <TextView
        android:id="@+id/tvName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/padding_medium"
        android:textSize="@dimen/text_item_title"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/tvCategory"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/margin_top_small"
        android:textSize="@dimen/text_caption" />

    <TextView
        android:id="@+id/tvLocation"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/margin_top_xsmall"
        android:textSize="@dimen/text_caption" />

    <TextView
        android:id="@+id/tvRating"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/margin_top_xsmall"
        android:textSize="@dimen/text_caption" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginTop="@dimen/padding_medium">

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
            android:text="Hapus" />

    </LinearLayout>

</LinearLayout>
```

- [ ] **Step 2: Create PlaceDetailBottomSheet.kt**

```kotlin
package com.example.spotlight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spotlight.databinding.BottomSheetPlaceDetailBinding
import com.example.spotlight.model.Place
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class PlaceDetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetPlaceDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val place = arguments?.getParcelable<Place>(ARG_PLACE) ?: return

        binding.imgPlace.setImageResource(place.imageRes)
        binding.tvName.text = place.name
        binding.tvCategory.text = place.category.name
        binding.tvLocation.text = place.location
        binding.tvRating.text = "⭐ ${place.rating}"

        binding.btnFavorite.setOnClickListener {
            Snackbar.make(view, "Ditambahkan ke favorit", Snackbar.LENGTH_SHORT).show()
        }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PLACE = "arg_place"

        fun newInstance(place: Place): PlaceDetailBottomSheet {
            return PlaceDetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLACE, place)
                }
            }
        }
    }
}
```

- [ ] **Step 3: Build and verify**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL.

---

### Task 7: Update PlaceAdapter + Final Wiring

**Files:**
- Modify: `app/src/main/java/com/example/spotlight/adapter/placeAdapter.kt`

- [ ] **Step 1: Update PlaceAdapter with callback and updateData**

```kotlin
package com.example.spotlight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spotlight.databinding.ItemPlaceBinding
import com.example.spotlight.model.Place

class PlaceAdapter(
    private var places: List<Place>,
    private val onItemClick: (Place) -> Unit
) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(private val binding: ItemPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            val (name, category, location, rating, imageRes) = place
            binding.tvName.text = name
            binding.tvCategory.text = category.name
            binding.tvLocation.text = location
            binding.tvRating.text = "⭐ $rating"
            binding.imgPlace.setImageResource(imageRes)

            binding.root.setOnClickListener {
                onItemClick(place)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount() = places.size

    fun updateData(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }
}
```

- [ ] **Step 2: Final build and verify**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL.

Full app flow:
1. Splash screen (2s) -> MainActivity
2. TopAppBar MD3 with "SpotLight" title + three-dot "Settings" option menu
3. Bottom nav: Home | About
4. Home: Sort button + 5 tabs (Semua, Cafe, Museum, Kuliner, Taman) with ViewPager2
5. Each tab shows filtered/sorted places in RecyclerView
6. Sort button: PopupMenu with 4 sort options
7. Tap place -> Modal Bottom Sheet with detail + "Tambah Favorit" (Snackbar) + "Hapus" (Dialog)
8. About: static text page

---

## Requirements Checklist

| Requirement | Task |
|---|---|
| Tab Layout + ViewPager2 | Task 4, 5 |
| Option Menu | Task 3 |
| TopAppBar MD3 (CenterAligned) | Task 3 |
| Dialog (MaterialAlertDialog) | Task 6 |
| Snackbar | Task 6 |
| Bottom Sheet (Modal) | Task 6 |
| Safe Args (plugin setup) | Task 1 |
| Sort/Filter by category + rating | Task 2, 4, 5 |
| Parcelize for data passing | Task 2 |
| Navigation Drawer | Skipped (< 5 destinations) |
