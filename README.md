Karchitec
================

#Kotlin RSS reader using Google android architecture components libraries

Pet project for testing several technologies:
- Kotlin
- MVVM architecture Slightly modifoed (It has a presenter :D)
- Google architecture components 
- Dagger 2 Dependency injection
- Constraint layout
- MockWebServer based Unit testing for RxJava (Pending)
- Espresso tests (Pending)
 
Simple RSS reader still in an early stage. It is mostly a pet project to test the architecture components in kotlin.
I've extracted the view to a decorator class in order to maintain the activities as clean as possible. I've also added a presenter for removing the not Data related logic from the ViewModel.


