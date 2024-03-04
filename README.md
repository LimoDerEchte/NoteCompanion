<h1>NoteCompanion</h1>
This is an android aimed at students who cant take proper notes in schook because most note taking apps dont support equations etc.<br>
The app is heavily work in progress and updated continously based on my own experiences in school.

<h2>Current and Planned Features</h4>
This is a list of all features in the app. This is updated frequently.

| Feature                 | Description                                         | Progress |
|-------------------------|-----------------------------------------------------|----------|
| LaTeX Equations (Short) | Renders LaTeX code that is modified for convenience | Done     |
| Chemical Reactions      | Renders Chemical Reactions from simple text         | Done     |
| Triangles Creator       | Enables you to create a Render of a given triangle  | Planned  |
| Graph Plotter           | Enables you to plot functions into a graph          | Planned  |
| Source in Steganography | Integrates the user input into the rendered image   | Planned  |


<h2>Feature Usage</h2>
<h3>LaTeX Quick Equation Editor</h3>
Tutorial WIP

<h3>Chemical Reaction Editor</h3>
You can write chemical components using a string like 3 H2O^2+ => 3 H<sub>2</sub>O<sup>2+</sup><br>
If you use --> a big line to the right will appear. This is also the center of alignment.<br><br>

Rules:
- A number in front of a letter is written like a letter.
- A number behind a non-space and non-number is <sub>lowered</sub>
- A number behind a ^ is <sup>ceiled</sup> (Including + and -)
- The --> is converted into the LaTeX \longrightarrow component
- Everything left to the arrow is aligned left
- Everything left to the arrow is aligned right
- The arrows are centered and below each other


