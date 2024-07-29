import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean player1Turn = true; // true means X's turn, false means O's turn
    private JLabel statusLabel = new JLabel("Player X's Turn");

    public TicTacToe() {
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 450); // Increased size to accommodate status label
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        // Initialize the buttons and add them to the panel
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                boardPanel.add(buttons[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        statusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        // Only allow the move if the button is not already clicked
        if (buttonClicked.getText().equals("")) {
            if (player1Turn) {
                buttonClicked.setText("X");
                statusLabel.setText("Player O's Turn");
            } else {
                buttonClicked.setText("O");
                statusLabel.setText("Player X's Turn");
            }
            player1Turn = !player1Turn;

            // Check for a win or draw
            if (checkForWin()) {
                String winner = player1Turn ? "O" : "X";
                JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                resetBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetBoard();
            }
        }
    }

    private boolean checkForWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (checkThree(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                    checkThree(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return true;
            }
        }

        // Check diagonals
        if (checkThree(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                checkThree(buttons[0][2], buttons[1][1], buttons[2][0])) {
            return true;
        }

        return false;
    }

    private boolean checkThree(JButton b1, JButton b2, JButton b3) {
        return !b1.getText().equals("") &&
                b1.getText().equals(b2.getText()) &&
                b2.getText().equals(b3.getText());
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true); // Re-enable buttons
            }
        }
        player1Turn = true;
        statusLabel.setText("Player X's Turn"); // Reset status label
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
