Этот проект — результат глубокого погружения в Kotlin Multiplatform (Android & Desktop). Скажу честно: путь был непростым, проект получился местами запутанным, а в коде до сих пор можно встретить "шрамы" от былых багов. 

###  Особенности проекта
* **Творческий хаос:** Архитектура эволюционировала в процессе обучения, поэтому структура может показаться перегруженной.
* **Археология кода:** Чтобы увидеть реализацию конкретных уроков (от навигации в Decompose до работы с DataStore), придется немного "покопаться" в файлах. Здесь нет стерильной чистоты, зато есть история реальной борьбы с `Unresolved reference`.

###  Где что искать
* **[commonMain](./composeApp/src/commonMain/kotlin)** — здесь живет вся общая логика. Ищите тут компоненты Decompose, настройки Ktor и сериализацию данных.
* **[androidMain](./composeApp/src/androidMain/kotlin)** — "сердце" Android-версии, работа с разрешениями, манифестом и точкой входа.
* **[jvmMain](./composeApp/src/jvmMain/kotlin)** — специфичные штуки для Desktop-версии.

### 🛠 Как запустить этот квест
Если вы решите собрать это локально, убедитесь, что ваш Gradle готов к приключениям:

**Android:**
- Windows: `.\gradlew.bat :composeApp:assembleDebug`
- macOS/Linux: `./gradlew :composeApp:assembleDebug`

**Desktop (JVM):**
- Windows: `.\gradlew.bat :composeApp:run`
- macOS/Linux: `./gradlew :composeApp:run`

---
*Проект построен на энтузиазме, кофе и бесконечных исправлениях импортов.*

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
