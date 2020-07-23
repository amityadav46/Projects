import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TTT extends JFrame implements ActionListener {
	private JButton[][] board;
	private boolean crossTurn;

	private final static int BOARD_SIZE = 3;
	private final static int CROSS_WINS = 1;
	private final static int ZERO_WINS = 2;
	private final static int INCOMPLETE = 3;
	private final static int DRAW = 4;

	public static void main(String[] args) {
		new TTT();
	}

	TTT() {
		super("Tic Tac Toe");

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(640, 640);
		setResizable(false);

		GridLayout grid = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		setLayout(grid);

		board = new JButton[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = new JButton();
				board[i][j].setFont(new Font("Times New Roman", 1, 225));
				add(board[i][j]);
			}
		}

		startGame();
	}

	private void startGame() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j].addActionListener(this);
			}
		}
		
		crossTurn = true;
	}

	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();

		if (clickedButton.getText().equals("")) {
			makeMove(clickedButton);

			// check if the game is finished
			int gameStatus = getGameStatus();

			if (gameStatus == INCOMPLETE) {
				// flip next Turn
				crossTurn = !crossTurn;
				this.setTitle(crossTurn? "X's turn": "0's turn");
			} else {
				declareWinner(gameStatus);
				dispose();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Invalid move.");
		}
	}

	private void makeMove(JButton source) {
		if (crossTurn) {
			source.setText("X");
		} else {
			source.setText("0");
		}
	}

	private int getGameStatus() {
		int i = 0, j = 0;
		String text1 = "", text2 = "";
		
		// rows
		for (i = 0; i < BOARD_SIZE; i++) {
			j = 0;
			while (j < BOARD_SIZE - 1) {
				text1 = this.board[i][j].getText();
				text2 = this.board[i][j + 1].getText();
				
				if (text1.equals("") || !text1.equals(text2)) {
					break;
				}

				j++;
			}

			if (j == BOARD_SIZE - 1) {
				return text1.equals("X")? CROSS_WINS : ZERO_WINS;
			}
		}

		// columns
		for (i = 0; i < BOARD_SIZE; i++) {
			j = 0;
			while (j < BOARD_SIZE - 1) {
				text1 = this.board[j][i].getText();
				text2 = this.board[j + 1][i].getText();
				
				if (text1.equals("") || !text1.equals(text2)) {
					break;
				}

				j++;
			}

			if (j == BOARD_SIZE - 1) {
				return text1.equals("X")? CROSS_WINS : ZERO_WINS;
			}
		}

		// diagonal 1
		i = 0;
		while (i < BOARD_SIZE - 1) {
			text1 = this.board[i][i].getText();
			text2 = this.board[i + 1][i + 1].getText();
			
			if (text1.equals("") || !text1.equals(text2)) {
				break;
			}

			i++;
		}

		if (i == BOARD_SIZE - 1) {
			return text1.equals("X")? CROSS_WINS : ZERO_WINS;
		}

		// diagonal 2
		i = 0;
		while (i < BOARD_SIZE - 1) {
			text1 = this.board[i][BOARD_SIZE - 1 - i].getText();
			text2 = this.board[i + 1][BOARD_SIZE - 1 - i - 1].getText();
			
			if (text1.equals("") || !text1.equals(text2)) {
				break;
			}

			i++;
		}

		if (i == BOARD_SIZE - 1) {
			return text1.equals("X")? CROSS_WINS : ZERO_WINS;
		}
		
		// Incomplete
		for (i = 0; i < BOARD_SIZE; i++) {
			for (j = 0; j < BOARD_SIZE; j++) {
				if (this.board[i][j].getText().equals("")) {
					return INCOMPLETE;
				}
			}
		}

		// if nothing else, then its a draw
		return DRAW;
	}

	private void declareWinner(int gameStatus) {
		if(gameStatus == DRAW){
			JOptionPane.showMessageDialog(null, "Draw.");
		}
		else if(gameStatus == CROSS_WINS){
			JOptionPane.showMessageDialog(null, "X all the way.");
		}
		else {
			JOptionPane.showMessageDialog(null, "0's rule.");
		}
	}
}
