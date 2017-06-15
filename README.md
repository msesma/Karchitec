Karchitec
================

## Kotlin RSS reader using Google android architecture components libraries

Pet project for testing several technologies:
- **Kotlin**
- **MVVM** architecture slightly modified (It has a presenter :D)
- **Google architecture components** : ViewModel, LiveData and Room
- **Dagger 2** Dependency injection (And Butterknife)
- **Retrofit 2**
- **Constraint layout**
- **Chrome Custom Tabs**
- **Stetho** for Okhttp and db debugging
- **MockWebServer** based **Unit testing**
- **Espresso** tests
- **Detekt** static code analysis
 
Simple RSS reader for technology testing. It is mostly a pet project to test the architecture components in kotlin.

Instead using a fragment as View, here we use a decorator class in order to maintain the activities as clean as possible. There is also a presenter for removing the not Data related logic from the ViewModel.

A time limited OkHttp caché acts as rate limiter avoiding network calls before 30 seconds after the last one. Some sites can overwrite this time.

More information about the architecture implemented in [this post](https://medium.com/proandroiddev/android-architecture-components-network-awareness-using-livedata-1a8d3749734d).
## Error management and network status: 
Instead creating an object that wraps network state and the actual data, here a second LiveData<NetworkError> is used. This LiveData is feeded by the repository with interpreted results of Network calls. This LiveData is also stored in the MainActivity ViewModel and the decorator subscribes to it as it does with the data one. this way, errors are also lifecycle aware.

This approach is simple and effective, keeping the responsibilities separated. Detail activity never does network calls so it doesn't use the error flow.

## Espresso tests: 
Creating a mock repository class that returns mock MutableLiveData with controlled data is enough to easily test anything we want on the UI. We coud even test errors mocking also the network error LiveData. 

I've left there the code for IdLingResource although it is not extrictly necessary using a fake Repository. With real repositories we must wait until the activity is iddle to ensure the data has arrived and the recyclerView is rendered.
## JUnit tests
Webservice and mappers (RefreshUseCase) are tested using MockWebServer and a TestExecutor.

![Alt text](./karchitec.png?raw=true "Architecture")
