# InsightNews 📰

**Native Android** application featuring AI-powered summarization, built with modern Android development best practices and senior-level architecture.

![Kotlin](https://img.shields.io/badge/Kotlin-1.9-7F52FF?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Compose-Material%203-4285F4)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVI-34A853)
![License](https://img.shields.io/badge/License-MIT-green)

## ✨ Features

### 📰 News Feed
- **Category filtering** (Tech, Business, Sports, Science, Health, etc.)
- **Search** functionality with real-time results
- **Infinite scroll** using Paging 3
- **Pull-to-refresh** for fresh content
- **Offline caching** via Room
- **Bias indicator** based on known source characteristics
- **Shimmer loading** and proper error handling

### 📄 Article Detail Screen
- **Full article** content display
- **AI-generated summary** at top (local NLP)
- **3–5 bullet point** insights
- **Reading time** estimation
- **Topic/keyword** extraction
- **Share** functionality
- **Bookmark** button

### 🔖 Bookmarks
- **Saved locally** via Room
- **Offline reading** support

### ♿ Accessibility
- **Text-to-Speech** support (framework ready)
- **Dark mode** (system-aware)
- Material 3 design

### 🤖 AI Summary Module
- **Separate** `feature_summary` module
- **SummarizationRepository** with extractive NLP
- **SummarizeArticleUseCase** 
- **SummaryViewModel** 
- Modular and testable design

### 🧠 Advanced Features
- **Background sync** every 6 hours (WorkManager)
- **Bias indicator** based on source
- **Sealed UI state** management (MVI)
- **Multi-module** architecture
- **Compose animations**
- **Performance optimizations**

## 🛠 Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Architecture | Clean Architecture + MVI |
| Async | Coroutines + Flow |
| DI | Hilt |
| Network | Retrofit |
| Local DB | Room |
| Paging | Paging 3 |
| Background | WorkManager |
| Testing | JUnit, Mockito, Compose UI Tests |
| Image Loading | Coil |

## 📁 Project Structure

```
app/
├── di/                 # App-level DI (API key)
├── ui/                 # Screens, navigation, theme
├── viewmodel/          # Shared ViewModels
└── worker/             # WorkManager workers

core/
├── domain/             # Domain models, repository interfaces
├── network/            # Retrofit API, DTOs, mappers
├── database/           # Room entities, DAOs
└── common/             # Utilities, base classes

feature/
├── news/               # News feed (MVI, Paging 3)
├── bookmark/           # Bookmarks (Room)
└── summary/            # AI summarization (NLP)
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or newer
- JDK 17+
- Android SDK 34

### API Key Setup (Required)

1. Get a free API key from [GNews](https://gnews.io/)
2. Open `local.properties` in the project root
3. Add your key:
   ```properties
   NEWS_API_KEY=your_actual_api_key_here
   ```
4. **Never** commit `local.properties` to version control (it's in `.gitignore`)

### Build & Run

```bash
./gradlew assembleDebug
```

Or use Android Studio: **Run > Run 'app'**

## 🏗 Architecture

### Clean Architecture Layers
- **Presentation** → ViewModels (MVI), Compose UI
- **Domain** → Use cases, repository interfaces, models (no Android deps)
- **Data** → Repository implementations, API, Database

### MVI Pattern
```kotlin
sealed class NewsUiState {
    data object Loading : NewsUiState()
    data class Success(val articles: Flow<PagingData<Article>>, ...) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

sealed class NewsUiEvent {
    data class SelectCategory(val category: NewsCategory) : NewsUiEvent()
    data class Search(val query: String) : NewsUiEvent()
    data object Refresh : NewsUiEvent()
}
```

## 📡 API Integration

Uses **GNews API** (free tier: 100 req/day, 10 articles/request)

- **Top Headlines**: `GET /api/v4/top-headlines?category=technology&apikey=KEY`
- **Search**: `GET /api/v4/search?q=android&apikey=KEY`

## 🧪 Testing

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

Tests include:
- `NewsViewModelTest` - MVI state transitions
- `SummarizeArticleUseCaseTest` - Use case logic
- `NewsScreenTest` - Compose UI test

## 📦 Key Deliverables

- ✅ Full folder structure
- ✅ Retrofit API interface (`GNewsApi`)
- ✅ DTO + Domain models
- ✅ Repository implementations
- ✅ Use cases (`SummarizeArticleUseCase`)
- ✅ MVI ViewModel
- ✅ Compose UI screens
- ✅ Sealed UI state
- ✅ WorkManager sync worker
- ✅ Unit + UI tests

## 📄 License

MIT License - see [LICENSE](LICENSE) for details.

---

**Built for showcasing senior Android engineering skills** 🚀
