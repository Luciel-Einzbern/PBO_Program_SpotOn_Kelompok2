package SpotOn;

/**
 * KELOMPOK 2
 * - NANANG SAEPUDIN   - 2510631170011
 * - FITRIA            - 2510631170023
 * - DIMAS INDRAWiJAYA - 2510631170032
 */

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormLogin implements ActionListener {

	JFrame frame;
	IconTextField txtUserID;
	IconPasswordField txtPassword;
	JLabel lblWelcome, lblLogo;
	JButton btnLogin, btnReset;
	JPanel panelKiri, panelKanan;

	public FormLogin() {
		frame = new JFrame("Form Login SpotOn");
		frame.setSize(650, 320);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);

		java.io.File fileAppIcon = new java.io.File("Icon App.png");
		if (fileAppIcon.exists()) {
			ImageIcon app = new ImageIcon("Icon App.png");
			frame.setIconImage(app.getImage());
		}

		panelKiri = new JPanel();
		panelKiri.setBounds(0, 0, 280, 320);
		panelKiri.setBackground(Color.WHITE);
		panelKiri.setLayout(null);
		frame.add(panelKiri);

		lblLogo = new JLabel();
		lblLogo.setBounds(40, 30, 200, 200);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

		try {
			ImageIcon imageIcon = new ImageIcon("SpotOn Logo.png");
			Image image = imageIcon.getImage();
			Image scaledImage = image.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			lblLogo.setIcon(new ImageIcon(scaledImage));
		} catch (Exception e) {
			lblLogo.setText("[ LOGO ]");
			lblLogo.setFont(new Font("Arial", Font.BOLD, 24));
		}
		panelKiri.add(lblLogo);

		JPanel garisTengah = new JPanel();
		garisTengah.setBounds(279, 0, 1, 320);
		garisTengah.setBackground(Color.LIGHT_GRAY);
		frame.add(garisTengah);

		panelKanan = new JPanel();
		panelKanan.setBounds(280, 0, 370, 320);
		panelKanan.setBackground(Color.WHITE);
		panelKanan.setLayout(null);
		frame.add(panelKanan);

		lblWelcome = new JLabel("Selamat Datang di SpotOn!");
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
		lblWelcome.setBounds(30, 40, 300, 25);
		panelKanan.add(lblWelcome);

		ImageIcon userIcon = new ImageIcon("User.png");
		ImageIcon lockIcon = new ImageIcon("PW.png");

		txtUserID = new IconTextField(userIcon);
		txtUserID.setForeground(Color.BLACK);
		txtUserID.setFont(new Font("Arial", Font.PLAIN, 13));
		txtUserID.setBounds(30, 80, 290, 35);
		panelKanan.add(txtUserID);

		txtPassword = new IconPasswordField(lockIcon);
		txtPassword.setForeground(Color.BLACK);
		txtPassword.setBounds(30, 130, 290, 35);
		panelKanan.add(txtPassword);

		btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(19, 27, 65));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBounds(30, 190, 140, 35);
		btnLogin.addActionListener(this);
		btnLogin.setFocusable(false);
		panelKanan.add(btnLogin);

		btnReset = new JButton("Reset");
		btnReset.setBackground(new Color(217, 222, 233));
		btnReset.setForeground(new Color(19, 27, 65));
		btnReset.setBounds(180, 190, 140, 35);
		btnReset.addActionListener(this);
		btnReset.setFocusable(false);
		panelKanan.add(btnReset);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			String username = txtUserID.getText();
			String password = new String(txtPassword.getPassword());

			Connection conn = Koneksi.getConnection();
			if (conn == null) {
				JOptionPane.showMessageDialog(frame, "Validasi gagal! Database tidak terhubung.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, username);
				pst.setString(2, password);

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					String posisiUser = rs.getString("posisi");
					JOptionPane.showMessageDialog(frame, "Login Berhasil! Selamat datang, " + username);

					frame.dispose();
					new MainGUI(posisiUser);
				} else {
					JOptionPane.showMessageDialog(frame, "Username atau Password salah!", "Login Gagal",
							JOptionPane.ERROR_MESSAGE);
				}

				rs.close();
				pst.close();
				conn.close();

			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(frame, "Terjadi kesalahan database: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == btnReset) {
			kosongkanForm();
		}
	}

	class IconTextField extends JTextField {
		private Icon icon;

		public IconTextField(Icon icon) {
			this.icon = icon;
			setMargin(new Insets(2, 30, 2, 2));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (icon != null) {
				int iconHeight = icon.getIconHeight();

				int x = 7;
				int y = (getHeight() - iconHeight) / 2;

				icon.paintIcon(this, g, x, y);
			}
		}
	}

	class IconPasswordField extends JPasswordField {
		private Icon icon;

		public IconPasswordField(Icon icon) {
			this.icon = icon;
			setMargin(new Insets(2, 30, 2, 2));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (icon != null) {
				int iconHeight = icon.getIconHeight();
				int x = 7;
				int y = (getHeight() - iconHeight) / 2;

				icon.paintIcon(this, g, x, y);
			}
		}
	}

	private void kosongkanForm() {
		txtUserID.setText("");
		txtPassword.setText("");
	}

	public static void main(String[] args) {
		new FormLogin();
	}
}
