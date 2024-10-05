Work on project. Stage 1/5:Stopwatch setup
Project: Stopwatch with Productivity Timer
Stopwatch setup

About
All of us sometimes want to study or work, but then something mysterious happens: first, we get distracted by a message from a friend, then we decide to briefly check our social networks, where we bump into some breaking news. Three hours later we find ourselves reading an article about the fall of the Roman Empire. It might be hard to finally stop procrastinating and spend time on really important things, and this is where time management comes in handy. One of the most famous technique of planning the time is Pomodoro.

Pomodoro consists of six steps:

Choose the task.
Set the pomodoro timer (typically for 25 minutes).
Work on the task.
Stop working when the timer goes off and put a checkmark on a piece of paper.
If you have fewer than four checkmarks, take a short break (3–5 minutes), then go to step 2.
After four pomodoros (that is, checkmarks), take a longer break (15–30 minutes), reset your checkmark count to zero, then go back to step 1.
Quite straightforward and helpful, isn’t it? At the end of this project, you will be able to implement an Android mobile application with a Pomodoro timer.

To save time and simplify the testing process, the app will be implemented on seconds instead of minutes.

In this stage, you should create and run a simple application with Android UI components (Views).

Description
In this stage, you should create two buttons and one label.

To display, start, and reset the timer, we need several components (Views) on the screen of the app. You should set up the environment (Android Studio and Virtual Device\Real Device), create a project with an empty activity, and put the following on the screen of this activity:

A button with attributes: text: Start, id: startButton.
A button with attributes: text: Reset, id: resetButton.
A TextView with attributes: id: textView.
All these Views should be placed inside the MainActivity class with the layout named activity_main. These class and layout already exist in the initial template you can start with.

Remember to set an id to these components because then we can test them!
