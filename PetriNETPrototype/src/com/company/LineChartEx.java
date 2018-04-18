package com.company;

/**
 * Created by Ovidiu on 3/19/2018.
 */
 import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class LineChartEx extends JFrame {

    String myNumeLocatie;
    Aplicatie myApp;
    int[][] myRegistru;

    public LineChartEx(String numeLocatie, int[][] registru, Aplicatie a) {

        this.myApp = a;
        this.myNumeLocatie = numeLocatie;
        this.myRegistru = registru;
        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset() {

        int inc = 0;

        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int i = 1; i < myApp.listaLocatii.listaLocatii.size(); i++) {
            if (myNumeLocatie.equals(myApp.listaLocatii.listaLocatii.get(i).nume)) {
                XYSeries series = new XYSeries(myApp.listaLocatii.listaLocatii.get(i).nume);
                for (int j = 1; j <= myApp.numarPasiSimulare; j++)
                    series.add(j, myRegistru[i][j]);
                dataset.addSeries(series);
            }
        }

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Evolutia jetoanelor pe locatii",
                "Timp",
                "Numar jetoane",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Evolutia jetoanelor pe locatii",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;

    }
}
