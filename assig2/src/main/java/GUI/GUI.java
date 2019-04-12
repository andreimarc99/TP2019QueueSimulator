package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

import ro.tuc.tp.assig2.Simulator;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {
	private JTextField minArrivalTime;
	private JTextField maxArrivalTime;
	private JTextField minServiceTime;
	private JTextField maxServiceTime;
	private static JTextField maxSimTime;
	private JTextField queueNr;
	private static  JTextArea eventsLog;
	private static JLabel avgWaitingResult;
	private static JLabel avgServiceResult;
	private static JLabel lblQueueResult;
	private static JLabel peakHourResult;
	private static JTextArea queueView; 
	
	public GUI() {
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Queue Processing");
		getContentPane().setForeground(new Color(0, 0, 0));
		getContentPane().setBackground(new Color(255, 255, 255));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1000, 600);
		getContentPane().setLayout(null);
		
		JLabel lblInputData = new JLabel("INPUT DATA");
		lblInputData.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInputData.setHorizontalAlignment(SwingConstants.CENTER);
		lblInputData.setBounds(10, 10, 254, 13);
		getContentPane().add(lblInputData);
		
		JLabel lblArrivalTime = new JLabel("Arrival Time");
		lblArrivalTime.setBounds(10, 33, 95, 21);
		getContentPane().add(lblArrivalTime);
		
		minArrivalTime = new JTextField();
		minArrivalTime.setBounds(107, 61, 68, 19);
		getContentPane().add(minArrivalTime);
		minArrivalTime.setColumns(10);
		
		JLabel lblMinArrival = new JLabel("Min");
		lblMinArrival.setBounds(107, 37, 46, 13);
		getContentPane().add(lblMinArrival);
		
		maxArrivalTime = new JTextField();
		maxArrivalTime.setBounds(196, 61, 68, 19);
		getContentPane().add(maxArrivalTime);
		maxArrivalTime.setColumns(10);
		
		JLabel lblMaxArrival = new JLabel("Max");
		lblMaxArrival.setBounds(196, 37, 46, 13);
		getContentPane().add(lblMaxArrival);
		
		JLabel lblServiceTime = new JLabel("Service Time");
		lblServiceTime.setBounds(10, 102, 95, 13);
		getContentPane().add(lblServiceTime);
		
		minServiceTime = new JTextField();
		minServiceTime.setBounds(107, 125, 68, 19);
		getContentPane().add(minServiceTime);
		minServiceTime.setColumns(10);
		
		JLabel lblMinService = new JLabel("Min");
		lblMinService.setBounds(107, 102, 46, 13);
		getContentPane().add(lblMinService);
		
		maxServiceTime = new JTextField();
		maxServiceTime.setBounds(196, 125, 68, 19);
		getContentPane().add(maxServiceTime);
		maxServiceTime.setColumns(10);
		
		JLabel lblMaxService = new JLabel("Max");
		lblMaxService.setBounds(196, 102, 46, 13);
		getContentPane().add(lblMaxService);
		
		JLabel lblMaximumSimulationTime = new JLabel("Simulation Time");
		lblMaximumSimulationTime.setBounds(10, 172, 165, 13);
		getContentPane().add(lblMaximumSimulationTime);
		
		maxSimTime = new JTextField();
		maxSimTime.setBounds(196, 169, 68, 19);
		getContentPane().add(maxSimTime);
		maxSimTime.setColumns(10);
		
		JButton btnStartSimulation = new JButton("Start Simulation");
		btnStartSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (minArrivalTime.getText().isEmpty() || maxArrivalTime.getText().isEmpty() 
						|| minServiceTime.getText().isEmpty() || maxServiceTime.getText().isEmpty()
						|| maxSimTime.getText().isEmpty() || queueNr.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please complete all the necessary fields");
				} else {
					try {
						int minArrival = Integer.parseInt(minArrivalTime.getText().trim());
						int maxArrival = Integer.parseInt(maxArrivalTime.getText().trim());
						
						int minService = Integer.parseInt(minServiceTime.getText().trim());
						int maxService = Integer.parseInt(maxServiceTime.getText().trim());
						
						int simTime = Integer.parseInt(maxSimTime.getText().trim());
						
						int nrOfQueues = Integer.parseInt(queueNr.getText().trim());
						
						if ((maxArrival >= minArrival) && (maxService >= minService) 
								&& (maxArrival < simTime) && (maxService < simTime)) {
							Simulator simulator = new Simulator(minArrival, maxArrival, minService, maxService, nrOfQueues, simTime);
							simulator.start();
						} else {
							JOptionPane.showMessageDialog(null, "You introduced wrong values");
						}
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Unable to convert fields");
					}
				}
			}
		});
		btnStartSimulation.setBounds(107, 249, 157, 21);
		getContentPane().add(btnStartSimulation);
		
		JLabel lblResults = new JLabel("RESULTS");
		lblResults.setBackground(new Color(255, 255, 255));
		lblResults.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblResults.setBounds(10, 315, 254, 13);
		getContentPane().add(lblResults);
		
		JLabel lblAverageWaitingTime = new JLabel("Average Waiting Time");
		lblAverageWaitingTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAverageWaitingTime.setBounds(10, 363, 165, 13);
		getContentPane().add(lblAverageWaitingTime);
		
		avgWaitingResult = new JLabel("-");
		avgWaitingResult.setHorizontalAlignment(SwingConstants.CENTER);
		avgWaitingResult.setBounds(196, 363, 68, 13);
		getContentPane().add(avgWaitingResult);
		
		JLabel lblAverageServiceTime = new JLabel("Average Service Time");
		lblAverageServiceTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAverageServiceTime.setBounds(10, 405, 165, 13);
		getContentPane().add(lblAverageServiceTime);
		
		avgServiceResult = new JLabel("-");
		avgServiceResult.setHorizontalAlignment(SwingConstants.CENTER);
		avgServiceResult.setBounds(196, 405, 68, 13);
		getContentPane().add(avgServiceResult);
		
		JLabel lblEmptyQueueTime = new JLabel("Empty Queue Time");
		lblEmptyQueueTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmptyQueueTime.setBounds(10, 447, 165, 13);
		getContentPane().add(lblEmptyQueueTime);
		
		lblQueueResult = new JLabel("-");
		lblQueueResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblQueueResult.setBounds(196, 447, 68, 13);
		getContentPane().add(lblQueueResult);
		
		JLabel lblPeakHour = new JLabel("Peak Hour");
		lblPeakHour.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPeakHour.setBounds(10, 489, 165, 13);
		getContentPane().add(lblPeakHour);
		
		peakHourResult = new JLabel("-");
		peakHourResult.setHorizontalAlignment(SwingConstants.CENTER);
		peakHourResult.setBounds(196, 489, 68, 13);
		getContentPane().add(peakHourResult);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(488, 276, 466, 226);
		getContentPane().add(scrollPane);
		
		eventsLog = new JTextArea();
		eventsLog.setFont(new Font("Arial", Font.PLAIN, 12));
		eventsLog.setEditable(false);
		scrollPane.setViewportView(eventsLog);
		
		DefaultCaret caret = (DefaultCaret) eventsLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JLabel lblEventsLog = new JLabel("Events Log");
		lblEventsLog.setBounds(488, 253, 124, 13);
		getContentPane().add(lblEventsLog);
		
		JLabel lblNumberOfQueues = new JLabel("Number of queues");
		lblNumberOfQueues.setBounds(10, 218, 165, 13);
		getContentPane().add(lblNumberOfQueues);
		
		queueNr = new JTextField();
		queueNr.setBounds(196, 215, 68, 19);
		getContentPane().add(queueNr);
		queueNr.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(488, 10, 466, 233);
		getContentPane().add(scrollPane_1);
		
		queueView = new JTextArea();
		queueView.setEditable(false);
		queueView.setFont(new Font("Arial Black", Font.PLAIN, 13));
		scrollPane_1.setViewportView(queueView);
	}
	
	public static JTextArea getEventsLog() {
		return eventsLog;
	}

	public static JLabel getAvgWaitingResult() {
		return avgWaitingResult;
	}
	
	public static JLabel getAvgServiceResult() {
		return avgServiceResult;
	}

	public static JLabel getLblQueueResult() {
		return lblQueueResult;
	}

	public static JLabel getPeakHourResult() {
		return peakHourResult;
	}

	public static JTextField getMaxSimTime() {
		return maxSimTime;
	}
	
	public static JTextArea getQueueView() {
		return queueView;
	}
}
