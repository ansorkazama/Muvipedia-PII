# Muvipedia
Android movie info browsing app version 2, presents content from TheMovieDb.org

## History
This application was submitted by Muhammad Ansharullah (ansorkazama@gmail.com) as the project:<b>Popular Movies-Phase 2</b> to compete the requirements of the Associate Android Developer Fast Track Nanodegree Program course.

## Building this application with Android Studio - prerequisite

<b>You will need an API key from TheMovieDB.org.  For this app, add your key to your grade.properties file:</b>

    /Users/Myname/.gradle/gradle.properties

with this identifier (edit in your key between the quotes):

     TheMovieDbApiToken="12341234123412341234"

## Criteria

The app spesification checks off the following requirements that were demanded by the Udacity Review teams:

**Common Project Requirements**
- [X] App is written solely in the Java Programming Language.
- [X] App conforms to common standards found in the [Android Nanodegree General Project Guidelines](http://udacity.github.io/android-nanodegree-guidelines/core.html)

**User Interface - Layout**

- [X] UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: **most popular, highest rated, and favorites**
- [X] Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails
- [X] UI contains a screen for displaying the details for a selected movie
- [X] Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
- [X] Movie Details layout contains a section for displaying trailer videos and user reviews

**User Interface - Function**
- [X] When a user changes the sort criteria (**most popular, highest rated, and favorites**) the main view gets updated correctly.
- [X] When a movie poster thumbnail is selected, the movie details screen is launched [Phone] or displayed in a fragment [Tablet]
- [X] When a trailer is selected, app uses an Intent to launch the trailer
- [X] In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite

**Network API Implementation**
- [X] In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.
- [X] App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.
- [X] App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

**Data Persistence**
- [X] The titles and ids of the user's favorite movies are stored in a **ContentProvider** backed by a SQLite database. This **ContentProvider** is updated whenever the user favorites or unfavorites a movie.
- [X] When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the **ContentProvider**.


#### Extended Criteria

**ContentProvider**
- [X] Extend the favorites **ContentProvider** to store the movie poster, synopsis, user rating, and release date, and display them even when offline.
- [X] Implement sharing functionality to allow the user to share the first trailer’s **YouTube** URL from the movie details screen.




## License and Disclaimers

Portions of this page are modifications based on work created and
shared by Google and used according to terms described in the Creative Commons 3.0 Attribution License.
See also the LICENSE.txt and LICENSECREATIVECOMMON.txt file at the top level of the repo.

** and **

Copyright (C) 2015 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## About / Attribution

### TheMovieDB

This Android Application downloads movie information from TheMovieDB.org.   Per the terms, the website 
requires posting this notice:

> Logos & Attribution
>- As per our terms of use, every application that uses our data or images is required to properly attribute TMDb as the source. Below you will find some logos you can use within your application.
![TheOpenMovieDB Logo](https://assets.tmdb.org/images/logos/var_1_1_PoweredByTMDB_Blk_Logo_Antitled.png)

### Icons

Icons used in this App were kindly provided by the Material Design team at Google.  They can be found at the following links:

https://www.google.com/design/spec/style/icons.html

https://github.com/google/material-design-icons/

### Picasso library

The truly excellent Picasso library took all the work out of downloading, caching, and inserting graphics from TheMovieDB into Views in the App.
A big "Thank You!" to the good folks at Square for their work.   Here is the web page link or just search for "Picasso" on GitHub:

http://square.github.io/picasso/


made with 💜 from Indonesia
