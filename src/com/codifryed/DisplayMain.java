package com.codifryed;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.*;
import static com.codifryed.PrimeFactoring.*;

public class DisplayMain {
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel consolePanel;
	private JTextArea consoleTextArea;

	public DisplayMain() {
		prepareGUI();
	}

	public static void main(String[] args) {
		DisplayMain display = new DisplayMain();
		display.showOptions();
		display.showConsole();
		display.redirectSystemStreams();

		System.out.println();
		System.out.println("Hello");
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			if (command.equals("1")) {
				statusLabel.setText("Single Thread processing...");
				threadedFactoring(1);
				
			} else if (command.equals("2")) {
				statusLabel.setText("Double Thread processing...");
				
			} else if (command.equals("4")) {
				statusLabel.setText("Quad Thread processing...");
			}
			else {
				throw new IllegalArgumentException("Button pushed that doesn't exist");
			}
		}
	}

	private void showConsole() {
		// headerLabel.setText("Control in action: JTextArea");
		// JLabel commentlabel= new JLabel("Comments: ", JLabel.RIGHT);

		consoleTextArea = new JTextArea("Factors:", 80, 80);

		JScrollPane scrollPane = new JScrollPane(consoleTextArea);
		// JButton showButton = new JButton("Show");

		// showButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// statusLabel.setText( consoleTextArea.getText());
		// }
		// });
		// controlPanel.add(commentlabel);
		consolePanel.add(scrollPane);
		// controlPanel.add(showButton);
		mainFrame.setVisible(true);

	}

	private void prepareGUI() {
		mainFrame = new JFrame("Prime Factoring with Concurrency");
		mainFrame.setSize(1024, 768);
		mainFrame.setLayout(new GridLayout(4, 1));
		// mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.Y_AXIS));

		headerLabel = new JLabel("", JLabel.CENTER);
		headerLabel.setSize(300, 25);
		headerLabel.setPreferredSize(headerLabel.getSize());
		headerLabel.setMaximumSize(headerLabel.getSize());
		headerLabel.setMinimumSize(headerLabel.getSize());
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(350, 100);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		consolePanel = new JPanel();

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(consolePanel);
		mainFrame.add(statusLabel);
		mainFrame.setLocationByPlatform(true);
		mainFrame.setVisible(true);
	}

	private void showOptions() {
		headerLabel.setText("Choose how many Threads to run:");

		JButton oneThread = new JButton("1");
		JButton twoThreads = new JButton("2");
		JButton fourThreads = new JButton("4");

		oneThread.setActionCommand("1");
		twoThreads.setActionCommand("2");
		fourThreads.setActionCommand("4");

		oneThread.addActionListener(new ButtonClickListener());
		twoThreads.addActionListener(new ButtonClickListener());
		fourThreads.addActionListener(new ButtonClickListener());

		controlPanel.add(oneThread);
		controlPanel.add(twoThreads);
		controlPanel.add(fourThreads);

		mainFrame.setVisible(true);

	}

	// The following codes set where the text get redirected. In this case,
	// jTextArea1
	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				consoleTextArea.append(text);
			}
		});
	}

	// Followings are The Methods that do the Redirect, you can simply Ignore them.
	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}
