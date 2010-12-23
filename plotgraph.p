set title "Iterations"
set xlabel "Iteration number"
set ylabel "Comm Status"

plot "iteration-data.dat" using 1:2 title 'Top' with lines, \
     "iteration-data.dat" using 1:3 title 'Ok' with linespoints, \
     "iteration-data.dat" using 1:4 title 'Error' with linespoints

