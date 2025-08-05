import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
    JButton[] buttons = new JButton[9];
    boolean playerXTurn = true;
    JLabel statusLabel;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Player X's Turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        Font buttonFont = new Font("Arial", Font.BOLD, 40);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(buttonFont);
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        add(panel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Restart");
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (!btn.getText().equals("")) {
            return; // Already clicked
        }

        btn.setText(playerXTurn ? "X" : "O");
        btn.setForeground(playerXTurn ? Color.BLUE : Color.RED);
        playerXTurn = !playerXTurn;

        statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");

        checkWinner();
    }

    void checkWinner() {
        String[][] board = new String[3][3];

        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText();
        }

        String winner = null;

        // Rows & Columns
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]))
                winner = board[i][0];
            if (!board[0][i].isEmpty() && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]))
                winner = board[0][i];
        }

        // Diagonals
        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
            winner = board[0][0];
        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
            winner = board[0][2];

        if (winner != null) {
            statusLabel.setText("Player " + winner + " Wins!");
            disableButtons();
        } else if (isDraw()) {
            statusLabel.setText("It's a Draw!");
        }
    }

    boolean isDraw() {
        for (JButton b : buttons) {
            if (b.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    void disableButtons() {
        for (JButton b : buttons) {
            b.setEnabled(false);
        }
    }

    void resetGame() {
        for (JButton b : buttons) {
            b.setText("");
            b.setEnabled(true);
        }
        playerXTurn = true;
        statusLabel.setText("Player X's Turn");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
