set title "Iterations"
set xlabel "Iteration number"
set ylabel "Comm Status"
set y2label "Fitness Status"
set y2tics 0, 0.1

  

plot \
     "iteration-data.dat" using 1:4 title 'Error'  axis x1y1 with lines, \
     "iteration-data.dat" using 1:3 title 'Ok'  axis x1y1 with lines, \
     "iteration-data.dat" using 1:5 title 'Fitness' axis x1y2 with lines

