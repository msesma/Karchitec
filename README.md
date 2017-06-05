Karchitec
================

#Kotlin RSS reader using Google android architecture components libraries

Pet project for testing several technologies:
- Kotlin
- MVVM architecture Slightly modified (It has a presenter :D)
- Google architecture components : ViewModel, LiveData and Room
- Dagger 2 Dependency injection (And Butterknife)
- Retrofit 2
- Constraint layout
- Chrome Custom Tabs
- Estetho for Okhttp and db debugging
- MockWebServer based Unit testing for RxJava
- Espresso tests
 
Simple RSS reader for tecnology testing. It is mostly a pet project to test the architecture components in kotlin.

I've extracted the view to a decorator class in order to maintain the activities as clean as possible. I've also added a presenter for removing the not Data related logic from the ViewModel.


#Pending work:
- Test: Expreso and JUnit