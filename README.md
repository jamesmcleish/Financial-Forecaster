<html>
</head>
    <h1>Monte Carlo Simulation - Readme</h1>
    <h2>Description</h2>
    <p>This Java application performs a Monte Carlo simulation to calculate the probabilities of different share prices after a series of steps. It takes user input for the number of steps (S) and the number of walks (N) and simulates share price changes. The application then displays a probability distribution table.</p>
    <h2>How to Run</h2>
        <li>Ensure you have JDK@17 installed on your system.</li>
        <li>Ensure you have Apache Maven 3.9.5 installed on your system. </li>
        <li>Compile the Java code using the following commands from inside this repository:</li>
        <code><pre>cd monte_carlo</code></pre>
        <pre><code>mvn clean install</code></pre>
        <li>Run the application with the following command from inside this repository:</li>
        <pre><code>java -jar target/monte_carlo-MINOR.jar</code></pre>
        <li>Follow the on-screen instructions to enter values for S and N when prompted.</li>
        <li>The application will display the probability distribution table in a pop-up window.</li>
        <li>If the number of steps (S) is even, it will also display the probability of the price returning to 100 after 10 steps.</li>
    <h2>Sample Output</h2>
    <pre><code>
    Enter a value for S (number of steps) (1-100): 10
    Enter a value for N (number of walks) (1-100000): 10000
    Probability of price equal to 100 after 10 steps = 0.2424
    </code></pre>
    <p>Probability :</p>
    <img src="price_probabilities_table.png">
    <h2>Author</h2>
    <p>Author: James McLeish</p>
</html>
