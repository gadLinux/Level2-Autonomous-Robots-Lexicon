set title "Iterations"
set xlabel "Iteration number"
set ylabel "Comm Status"
set y2label "Fitness Status"
set y2tics 0, 0.1

  

plot \
     "iteration-data.dat" using 1:4 title 'Error'  axis x1y1 with lines, \
     "iteration-data.dat" using 1:3 title 'Ok'  axis x1y1 with lines, \
     "iteration-data.dat" using 1:5 title 'Fitness' axis x1y2 with lines

set terminal postscript eps color enhanced dashed lw 1 "Helvetica" 14 
set output "/home/gaguilar/Ubuntu One/Trabajos/Robots Autónomos/graphs/plot.eps"

replot
set terminal x11
set size 1,1
