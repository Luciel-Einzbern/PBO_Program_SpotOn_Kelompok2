package SpotOn;

/**
 *  KELOMPOK 2
 *  - NANANG SAEPUDIN   - 2510631170011
 *  - FITRIA            - 2510631170023
 *  - DIMAS INDRAWiJAYA - 2510631170032
 */

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.Duration;

public class MainGUI implements ActionListener {

	JFrame frame;
	JPanel leftPanel, rightContainer;
	CardLayout cardLayout;

	JPanel panelLayanan, panelBulanan, panelHarian, panelAdmin;

	JButton btnLayanan, btnBulanan, btnHarian, btnAdmin;

	JComboBox<String> comboMenu, comboJenis;
	JTextField textPlat;
	JButton confirmBT, resetBT, btnDenah;
	JTextArea textLogLayanan;
	JScrollPane scrollLayanan;

	JTable tabelBulanan;
	DefaultTableModel modelTabelBulanan;
	JScrollPane scrollTabelBulanan;
	JLabel lblGrandTotal;

	JComboBox<Integer> comboHariHarian;
	JTable tabelHarian;
	DefaultTableModel modelTabelHarian;
	JScrollPane scrollTabelHarian;

	JComboBox<Integer> comboHariAdmin;
	JTextField textDetik;
	JButton confirmAdminBT, resetAdminBT;

	Font fontTitle = new Font("Helvetica Roman", Font.BOLD, 18);
	Font fontLabel = new Font("Helvetica Roman", Font.BOLD, 13);
	Font fontComponent = new Font("Helvetica Roman", Font.PLAIN, 13);
	Font fontConsole = new Font("Consolas", Font.PLAIN, 12);

	Color colorSidebar = new Color(33, 37, 41); // Dark Charcoal
	Color colorBackground = new Color(248, 249, 250); // Light Gray
	Color colorAccent = new Color(0, 123, 255); // Electric Blue
	Color colorWhite = Color.WHITE;

	private String posisiUser;

	public MainGUI(String posisiUser) {
		this.posisiUser = posisiUser;

		frame = new JFrame("Sistem Parkir SpotOn! - Dashboard Utama");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(850, 550);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);

		java.io.File fileAppIcon = new java.io.File("Icon App.png");
		if (fileAppIcon.exists()) {
			ImageIcon app = new ImageIcon("Icon App.png");
			frame.setIconImage(app.getImage());
		}

		leftPanel = new JPanel();
		leftPanel.setBounds(0, 0, 240, 550);
		leftPanel.setBackground(colorSidebar);
		leftPanel.setLayout(null);

		JLabel lblLogoGambar = new JLabel();
		lblLogoGambar.setBounds(45, 20, 150, 150);

		java.io.File fileLogo = new java.io.File("SpotOn Logo.png");
		if (fileLogo.exists()) {
			ImageIcon icon = new ImageIcon("SpotOn Logo.png");
			Image imgSkala = icon.getImage().getScaledInstance(lblLogoGambar.getWidth(), lblLogoGambar.getHeight(),
					Image.SCALE_SMOOTH);
			lblLogoGambar.setIcon(new ImageIcon(imgSkala));
		} else {
			lblLogoGambar.setText("SpotOn! Logo");
			lblLogoGambar.setForeground(colorWhite);
			lblLogoGambar.setFont(fontTitle);
			lblLogoGambar.setHorizontalAlignment(SwingConstants.CENTER);
			lblLogoGambar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		}
		leftPanel.add(lblLogoGambar);

		JLabel lblNavTitle1 = new JLabel("LAYANAN UTAMA");
		lblNavTitle1.setForeground(Color.GRAY);
		lblNavTitle1.setFont(new Font("Helvetica Roman", Font.BOLD, 11));
		lblNavTitle1.setBounds(20, 190, 200, 20);
		leftPanel.add(lblNavTitle1);

		btnLayanan = new JButton("Layanan Parkir");
		btnLayanan.setBounds(20, 215, 200, 40);
		konfigurasiTombolNavigasi(btnLayanan);

		JLabel lblNavTitle2 = new JLabel("LAPORAN & RIWAYAT");
		lblNavTitle2.setForeground(Color.GRAY);
		lblNavTitle2.setFont(new Font("Helvetica Roman", Font.BOLD, 11));
		lblNavTitle2.setBounds(20, 280, 200, 20);
		leftPanel.add(lblNavTitle2);

		btnBulanan = new JButton("Riwayat Bulanan");
		btnBulanan.setBounds(20, 305, 200, 40);
		konfigurasiTombolNavigasi(btnBulanan);

		btnHarian = new JButton("Riwayat Harian");
		btnHarian.setBounds(20, 355, 200, 40);
		konfigurasiTombolNavigasi(btnHarian);

		JLabel lblNavTitle3 = new JLabel("PENGATURAN SISTEM");
		lblNavTitle3.setForeground(Color.GRAY);
		lblNavTitle3.setFont(new Font("Helvetica Roman", Font.BOLD, 11));
		lblNavTitle3.setBounds(20, 420, 200, 20);
		leftPanel.add(lblNavTitle3);

		btnAdmin = new JButton("Admin Menu");
		btnAdmin.setBounds(20, 445, 200, 40);
		konfigurasiTombolNavigasi(btnAdmin);

		cardLayout = new CardLayout();
		rightContainer = new JPanel(cardLayout);
		rightContainer.setBounds(240, 0, 610, 550);

		inisialisasiHalamanLayanan();
		inisialisasiHalamanBulanan();
		inisialisasiHalamanHarian();
		inisialisasiHalamanAdmin();

		rightContainer.add(panelLayanan, "Layanan");
		rightContainer.add(panelBulanan, "Bulanan");
		rightContainer.add(panelHarian, "Harian");
		rightContainer.add(panelAdmin, "Admin");

		frame.add(leftPanel);
		frame.add(rightContainer);

		if (cardLayout != null && rightContainer != null) {
			cardLayout.show(rightContainer, "Layanan");
		}

		if ("petugas".equalsIgnoreCase(this.posisiUser)) {
			if (btnAdmin != null) {
				btnAdmin.setVisible(false);
			}
			lblNavTitle3.setVisible(false);
		}

		btnLayanan.setBackground(colorAccent);
		btnLayanan.setForeground(colorWhite);

		perbaruiTampilanTabelHarian();
		perbaruiTampilanTabelBulanan();

		frame.setVisible(true);
	}

	private void konfigurasiTombolNavigasi(JButton button) {
		button.setFocusable(false);
		button.setFont(fontComponent);
		button.setBackground(new Color(49, 54, 59));
		button.setForeground(new Color(206, 212, 218));
		button.setBorderPainted(false);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setMargin(new Insets(0, 15, 0, 0));
		button.setFocusable(false);
		button.addActionListener(this);
		leftPanel.add(button);
	}

	private void resetWarnaTombolNavigasi() {
		Color colorDefaultBg = new Color(49, 54, 59);
		Color colorDefaultFg = new Color(206, 212, 218);
		btnLayanan.setBackground(colorDefaultBg);
		btnLayanan.setForeground(colorDefaultFg);
		btnBulanan.setBackground(colorDefaultBg);
		btnBulanan.setForeground(colorDefaultFg);
		btnHarian.setBackground(colorDefaultBg);
		btnHarian.setForeground(colorDefaultFg);
		btnAdmin.setBackground(colorDefaultBg);
		btnAdmin.setForeground(colorDefaultFg);
	}

	private void inisialisasiHalamanLayanan() {
		panelLayanan = new JPanel(null);
		panelLayanan.setBackground(colorBackground);

		JLabel title = new JLabel("Layanan Operasional Parkir");
		title.setFont(fontTitle);
		title.setBounds(25, 20, 300, 30);

		JLabel lblMenu = new JLabel("Menu Transaksi");
		lblMenu.setFont(fontLabel);
		lblMenu.setBounds(25, 70, 150, 20);
		String[] menuOptions = { "Masuk", "Keluar", "Tracking" };
		comboMenu = new JComboBox<>(menuOptions);
		comboMenu.setFont(fontComponent);
		comboMenu.setBounds(25, 95, 160, 30);

		JLabel lblJenis = new JLabel("Jenis Kendaraan");
		lblJenis.setFont(fontLabel);
		lblJenis.setBounds(210, 70, 150, 20);
		String[] jenisOptions = { "Mobil", "Motor" };
		comboJenis = new JComboBox<>(jenisOptions);
		comboJenis.setFont(fontComponent);
		comboJenis.setBounds(210, 95, 160, 30);

		JLabel lblPlat = new JLabel("Input Plat Nomor");
		lblPlat.setFont(fontLabel);
		lblPlat.setBounds(395, 70, 150, 20);
		textPlat = new JTextField();
		textPlat.setFont(fontComponent);
		textPlat.setBounds(395, 95, 175, 30);

		btnDenah = new JButton("Denah Parkir");
		btnDenah.setFont(fontComponent);
		btnDenah.setBackground(new Color(108, 117, 125));
		btnDenah.setForeground(colorWhite);
		btnDenah.setBounds(25, 145, 160, 35);
		btnDenah.setFocusable(false);
		btnDenah.addActionListener(this);

		confirmBT = new JButton("Confirm");
		confirmBT.setFont(fontComponent);
		confirmBT.setBackground(new Color(40, 167, 69));
		confirmBT.setForeground(colorWhite);
		confirmBT.setBounds(380, 145, 95, 35);
		confirmBT.setFocusable(false);
		confirmBT.addActionListener(this);

		resetBT = new JButton("Reset");
		resetBT.setFont(fontComponent);
		resetBT.setBackground(new Color(220, 53, 69));
		resetBT.setForeground(colorWhite);
		resetBT.setBounds(485, 145, 85, 35);
		resetBT.setFocusable(false);
		resetBT.addActionListener(this);

		textLogLayanan = new JTextArea();
		textLogLayanan.setEditable(false);
		textLogLayanan.setFont(fontConsole);
		textLogLayanan.setBackground(new Color(30, 30, 30));
		textLogLayanan.setForeground(new Color(0, 255, 64));
		textLogLayanan.setMargin(new Insets(10, 10, 10, 10));

		scrollLayanan = new JScrollPane(textLogLayanan);
		scrollLayanan.setBounds(25, 200, 545, 290);

		panelLayanan.add(title);
		panelLayanan.add(lblMenu);
		panelLayanan.add(comboMenu);
		panelLayanan.add(lblJenis);
		panelLayanan.add(comboJenis);
		panelLayanan.add(lblPlat);
		panelLayanan.add(textPlat);
		panelLayanan.add(btnDenah);
		panelLayanan.add(confirmBT);
		panelLayanan.add(resetBT);
		panelLayanan.add(scrollLayanan);

		textLogLayanan.append("[SYSTEM] SpotOn Console Ready. Menunggu instruksi data...\n");
	}

	private void inisialisasiHalamanBulanan() {
		panelBulanan = new JPanel(null);
		panelBulanan.setBackground(colorBackground);

		JLabel title = new JLabel("Riwayat Data Bulanan");
		title.setFont(fontTitle);
		title.setBounds(25, 20, 400, 30);
		panelBulanan.add(title);

		String[] headersBulanan = { "Hari", "Total Omset Harian" };
		modelTabelBulanan = new DefaultTableModel(headersBulanan, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabelBulanan = new JTable(modelTabelBulanan);
		tabelBulanan.setFont(fontComponent);
		tabelBulanan.setRowHeight(22);
		tabelBulanan.getTableHeader().setFont(fontLabel);

		scrollTabelBulanan = new JScrollPane(tabelBulanan);
		scrollTabelBulanan.setBounds(25, 70, 545, 370);
		panelBulanan.add(scrollTabelBulanan);

		lblGrandTotal = new JLabel("Grand Total Pendapatan Bulanan: Rp 0");
		lblGrandTotal.setFont(new Font("Helvetica Roman", Font.BOLD, 14));
		lblGrandTotal.setForeground(new Color(40, 167, 69));
		lblGrandTotal.setBounds(25, 455, 545, 25);
		panelBulanan.add(lblGrandTotal);
	}

	private void inisialisasiHalamanHarian() {
		panelHarian = new JPanel(null);
		panelHarian.setBackground(colorBackground);

		JLabel title = new JLabel("Riwayat Kendaraan Tanggal");
		title.setFont(fontTitle);
		title.setBounds(25, 20, 260, 30);

		comboHariHarian = new JComboBox<>();
		for (int i = 1; i <= 30; i++)
			comboHariHarian.addItem(i);
		comboHariHarian.setFont(fontComponent);
		comboHariHarian.setBounds(290, 22, 80, 28);
		comboHariHarian.addActionListener(e -> perbaruiTampilanTabelHarian());

		String[] namaKolom = { "ID", "Plat Nomor", "Jenis", "Waktu Masuk", "Waktu Keluar", "Tarif" };
		modelTabelHarian = new DefaultTableModel(namaKolom, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabelHarian = new JTable();
		tabelHarian.setModel(modelTabelHarian);
		tabelHarian.setFont(fontComponent);
		tabelHarian.setRowHeight(22);
		tabelHarian.getTableHeader().setFont(fontLabel);

		scrollTabelHarian = new JScrollPane(tabelHarian);
		scrollTabelHarian.setBounds(25, 70, 545, 420);

		panelHarian.add(title);
		panelHarian.add(comboHariHarian);
		panelHarian.add(scrollTabelHarian);
	}

	private void inisialisasiHalamanAdmin() {
		panelAdmin = new JPanel(null);
		panelAdmin.setBackground(colorBackground);

		JLabel title = new JLabel("Developer & Admin Menu");
		title.setFont(fontTitle);
		title.setBounds(25, 20, 300, 30);

		JLabel lblHari = new JLabel("Simulasi Posisi Hari/Tanggal (1-30) :");
		lblHari.setFont(fontLabel);
		lblHari.setBounds(25, 80, 250, 20);

		comboHariAdmin = new JComboBox<>();
		for (int i = 1; i <= 30; i++)
			comboHariAdmin.addItem(i);
		comboHariAdmin.setFont(fontComponent);
		comboHariAdmin.setBounds(280, 75, 100, 30);

		JLabel lblDetik = new JLabel("Konfigurasi Waktu (1 Jam = ... Detik) :");
		lblDetik.setFont(fontLabel);
		lblDetik.setBounds(25, 130, 250, 20);

		textDetik = new JTextField();
		textDetik.setFont(fontComponent);
		textDetik.setBounds(280, 125, 100, 30);

		confirmAdminBT = new JButton("Confirm");
		confirmAdminBT.setFont(fontComponent);
		confirmAdminBT.setBackground(new Color(40, 167, 69));
		confirmAdminBT.setForeground(colorWhite);
		confirmAdminBT.setBounds(150, 190, 100, 35);
		confirmAdminBT.setFocusable(false);
		confirmAdminBT.addActionListener(this);

		resetAdminBT = new JButton("Reset");
		resetAdminBT.setFont(fontComponent);
		resetAdminBT.setBackground(new Color(220, 53, 69));
		resetAdminBT.setForeground(colorWhite);
		resetAdminBT.setBounds(265, 190, 90, 35);
		resetAdminBT.setFocusable(false);
		resetAdminBT.addActionListener(this);

		panelAdmin.add(title);
		panelAdmin.add(lblHari);
		panelAdmin.add(comboHariAdmin);
		panelAdmin.add(lblDetik);
		panelAdmin.add(textDetik);
		panelAdmin.add(confirmAdminBT);
		panelAdmin.add(resetAdminBT);
	}

	private void tampilkanWindowDenah() {
		JDialog dialogDenah = new JDialog(frame, "Peta Ketersediaan Slot Parkir", true);
		dialogDenah.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialogDenah.setResizable(false);
		dialogDenah.setSize(600, 450);
		dialogDenah.setLayout(null);
		dialogDenah.setLocationRelativeTo(frame);
		dialogDenah.getContentPane().setBackground(colorBackground);

		JLabel lblTitle = new JLabel("Peta Ketersediaan Slot Parkir");
		lblTitle.setFont(fontTitle);
		lblTitle.setBounds(20, 15, 300, 30);
		dialogDenah.add(lblTitle);

		JLabel lblFilter = new JLabel("Jenis Kendaraan:");
		lblFilter.setFont(fontLabel);
		lblFilter.setBounds(20, 60, 150, 25);
		dialogDenah.add(lblFilter);

		JComboBox<String> comboJenisDenah = new JComboBox<>(new String[] { "Mobil", "Motor" });
		comboJenisDenah.setFont(fontComponent);
		comboJenisDenah.setBounds(150, 60, 120, 25);
		dialogDenah.add(comboJenisDenah);

		String[] headerDenah = { "No.", "Status", "Jenis Kendaraan", "Plat Nomor" };
		DefaultTableModel modelDenah = new DefaultTableModel(headerDenah, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable tabelDenah = new JTable(modelDenah);
		tabelDenah.setFont(fontComponent);
		tabelDenah.setRowHeight(22);
		tabelDenah.getTableHeader().setFont(fontLabel);

		JScrollPane scrollDenah = new JScrollPane(tabelDenah);
		scrollDenah.setBounds(20, 100, 545, 290);
		dialogDenah.add(scrollDenah);

		comboJenisDenah.addActionListener(e -> {
			perbaruiTabelDenah(modelDenah, comboJenisDenah.getSelectedItem().toString());
		});

		perbaruiTabelDenah(modelDenah, "Mobil");
		dialogDenah.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source == btnLayanan) {
			resetWarnaTombolNavigasi();
			btnLayanan.setBackground(colorAccent);
			btnLayanan.setForeground(colorWhite);
			cardLayout.show(rightContainer, "Layanan");
		}

		if (source == btnBulanan) {
			resetWarnaTombolNavigasi();
			btnBulanan.setBackground(colorAccent);
			btnBulanan.setForeground(colorWhite);
			cardLayout.show(rightContainer, "Bulanan");
			perbaruiTampilanTabelBulanan();
		}

		if (source == btnHarian) {
			resetWarnaTombolNavigasi();
			btnHarian.setBackground(colorAccent);
			btnHarian.setForeground(colorWhite);
			cardLayout.show(rightContainer, "Harian");
			perbaruiTampilanTabelHarian();
		}

		if (source == btnAdmin) {
			resetWarnaTombolNavigasi();
			btnAdmin.setBackground(colorAccent);
			btnAdmin.setForeground(colorWhite);
			cardLayout.show(rightContainer, "Admin");
			comboHariAdmin.setSelectedIndex(DatabaseParkir.hariIni);
		}

		if (source == btnDenah) {
			tampilkanWindowDenah();
		}

		if (source == confirmBT) {
			String menu = comboMenu.getSelectedItem().toString();
			String jenis = comboJenis.getSelectedItem().toString();
			String plat = textPlat.getText().trim();

			if (plat.isEmpty()) {
				textLogLayanan.append("[ERROR] Kolom Plat nomor kosong. Silakan input ulang.\n");
				return;
			}

			if (menu.equals("Masuk")) {

				boolean platSudahAda = false;
				for (Mobil m : DatabaseParkir.slotMobil) {
					if (m.getPlatNomor().equalsIgnoreCase(plat))
						platSudahAda = true;
				}
				for (Motor m : DatabaseParkir.slotMotor) {
					if (m.getPlatNomor().equalsIgnoreCase(plat))
						platSudahAda = true;
				}

				if (platSudahAda) {
					textLogLayanan.append(">> [ERROR] Kendaraan dengan plat " + plat + " sudah ada di parkiran!\n");
					return;
				}

				if (jenis.equals("Mobil")) {
					if (DatabaseParkir.slotMobil.size() < 20) {
						int srp = 1;
						for (int i = 1; i <= 20; i++) {
							boolean terisi = false;
							for (Mobil m : DatabaseParkir.slotMobil) {
								if (m.getLokasiParkir() == i) {
									terisi = true;
									break;
								}
							}
							if (!terisi) {
								srp = i;
								break;
							}
						}
						DatabaseParkir.slotMobil.add(new Mobil(plat, srp));
						textLogLayanan.append(">> [IN] Mobil [" + plat + "] berhasil parkir di SRP C-" + srp + "\n");
					} else {
						textLogLayanan.append(">> [FAILED] Slot Parkir khusus Mobil sudah penuh!\n");
					}
				} else {
					if (DatabaseParkir.slotMotor.size() < 50) {
						int srp = 1;
						for (int i = 1; i <= 50; i++) {
							boolean terisi = false;
							for (Motor m : DatabaseParkir.slotMotor) {
								if (m.getLokasiParkir() == i) {
									terisi = true;
									break;
								}
							}
							if (!terisi) {
								srp = i;
								break;
							}
						}
						DatabaseParkir.slotMotor.add(new Motor(plat, srp));
						textLogLayanan.append(">> [IN] Motor [" + plat + "] berhasil parkir di SRP B-" + srp + "\n");
					} else {
						textLogLayanan.append(">> [FAILED] Slot Parkir khusus Motor sudah penuh!\n");
					}
				}
			}

			if (menu.equals("Keluar")) {
				String plat1 = textPlat.getText().trim().toUpperCase();
				if (plat1.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Masukkan plat nomor terlebih dahulu!");
					return;
				}

				Mobil mobilKeluar = null;
				Motor motorKeluar = null;

				if (comboJenis.getSelectedItem().equals("Mobil")) {
					for (Mobil m : DatabaseParkir.slotMobil) {
						if (m.getPlatNomor().equals(plat1)) {
							mobilKeluar = m;
							break;
						}
					}
				} else {
					for (Motor m : DatabaseParkir.slotMotor) {
						if (m.getPlatNomor().equals(plat1)) {
							motorKeluar = m;
							break;
						}
					}
				}

				if (mobilKeluar == null && motorKeluar == null) {
					JOptionPane.showMessageDialog(frame, "Kendaraan dengan plat " + plat1 + " tidak ditemukan!");
					return;
				}

				LocalDateTime waktuMasuk = (mobilKeluar != null) ? mobilKeluar.getWaktuMasuk()
						: motorKeluar.getWaktuMasuk();
				LocalDateTime waktuKeluar = DatabaseParkir.getWaktuSistem();
				long selisihDetik = Duration.between(waktuMasuk, waktuKeluar).getSeconds();

				double jamSimulasi = (double) selisihDetik
						/ (DatabaseParkir.waktuDipercepat ? (DatabaseParkir.detikPerJamSimulasi / 3600.0) : 3600.0);
				if (jamSimulasi < 0.01)
					jamSimulasi = 0.01;

				int jamBulat = (int) Math.ceil(jamSimulasi);
				double tarifPerJam = (mobilKeluar != null) ? mobilKeluar.getTarifPerJam()
						: motorKeluar.getTarifPerJam();
				double totalTarif = jamBulat * tarifPerJam;

				String jenisStr = (mobilKeluar != null) ? "Mobil" : "Motor";
				int lokasi = (mobilKeluar != null) ? mobilKeluar.getLokasiParkir() : motorKeluar.getLokasiParkir();
				String kodeLokasi = (mobilKeluar != null ? "C-" : "B-") + lokasi;

				String ringkasan = String.format(
						"=== KONFIRMASI KELUAR ===\n\n" + "%s %-15s : %s\n" + "%s %-15s : %s\n" + "%s %-15s : %s\n"
								+ "%s %-15s : %s\n" + "%s %-15s : %s\n" + "%s %-15s : %.2f Jam (%d Jam)\n"
								+ "%s %-15s : Rp %.0f\n\n" + "Apakah pelanggan sudah membayar?",
						"", "Plat Nomor", plat, "", "Jenis", jenisStr, "", "Lokasi Slot", kodeLokasi, "", "Waktu Masuk",
						waktuMasuk.format(DatabaseParkir.formatWaktu), "", "Waktu Keluar",
						waktuKeluar.format(DatabaseParkir.formatWaktu), "", "Lama Parkir", jamSimulasi, jamBulat, "",
						"Total Tarif", totalTarif);

				JTextArea textArea = new JTextArea(ringkasan);
				textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
				textArea.setEditable(false);

				int opsi = JOptionPane.showConfirmDialog(frame, textArea, "Konfirmasi Pembayaran",
						JOptionPane.YES_NO_OPTION);

				if (opsi == JOptionPane.YES_OPTION) {

					try {
						Connection conn = Koneksi.getConnection();

						if (conn != null) {
							int hariUntukDatabase = DatabaseParkir.hariIni + 1;

							String sqlHarian = "INSERT INTO riwayat_harian (hari_ke, plat_nomor, jenis_kendaraan, waktu_masuk, waktu_keluar, tarif) VALUES (?, ?, ?, ?, ?, ?)";
							PreparedStatement pstHarian = conn.prepareStatement(sqlHarian);
							pstHarian.setInt(1, hariUntukDatabase);
							pstHarian.setString(2, plat);
							pstHarian.setString(3, jenisStr);
							pstHarian.setTimestamp(4, java.sql.Timestamp.valueOf(waktuMasuk));
							pstHarian.setTimestamp(5, java.sql.Timestamp.valueOf(waktuKeluar));
							pstHarian.setDouble(6, totalTarif);
							pstHarian.executeUpdate();
							pstHarian.close();

							String sqlBulanan = "INSERT INTO riwayat_bulanan (hari_ke, total_pendapatan) VALUES (?, ?) "
									+ "ON DUPLICATE KEY UPDATE total_pendapatan = total_pendapatan + ?";
							PreparedStatement pstBulanan = conn.prepareStatement(sqlBulanan);
							pstBulanan.setInt(1, hariUntukDatabase);
							pstBulanan.setDouble(2, totalTarif);
							pstBulanan.setDouble(3, totalTarif);
							pstBulanan.executeUpdate();
							pstBulanan.close();

							conn.close();

							perbaruiTampilanTabelHarian();
							perbaruiTampilanTabelBulanan();
						}
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(frame, "Gagal menyimpan riwayat ke database: " + ex.getMessage(),
								"Error Database", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (mobilKeluar != null) {
						DatabaseParkir.slotMobil.remove(mobilKeluar);
					} else {
						DatabaseParkir.slotMotor.remove(motorKeluar);
					}

					textLogLayanan.append("[" + waktuKeluar.format(DatabaseParkir.formatWaktu) + "] KELUAR: " + jenisStr
							+ " [" + plat + "] di slot " + kodeLokasi + ". Total Tarif: Rp " + totalTarif + "\n");
					JOptionPane.showMessageDialog(frame, "Kendaraan berhasil keluar. Sampai jumpa!");
					textPlat.setText("");
				}
			}

			if (menu.equals("Tracking")) {
				boolean statusKetemu = false;
				for (int i = 0; i < DatabaseParkir.slotMobil.size(); i++) {
					if (DatabaseParkir.slotMobil.get(i).getPlatNomor().equalsIgnoreCase(plat)) {
						textLogLayanan.append(">> [TRACK] Kendaraan Mobil [" + plat + "] aktif di Slot C-"
								+ DatabaseParkir.slotMobil.get(i).getLokasiParkir() + "\n");
						statusKetemu = true;
						break;
					}
				}
				if (!statusKetemu) {
					for (int i = 0; i < DatabaseParkir.slotMotor.size(); i++) {
						if (DatabaseParkir.slotMotor.get(i).getPlatNomor().equalsIgnoreCase(plat)) {
							textLogLayanan.append(">> [TRACK] Kendaraan Motor [" + plat + "] aktif di Slot B-"
									+ DatabaseParkir.slotMotor.get(i).getLokasiParkir() + "\n");
							statusKetemu = true;
							break;
						}
					}
				}
				if (!statusKetemu) {
					textLogLayanan.append(">> [TRACK] Kendaraan " + plat + " tidak ditemukan.\n");
				}
			}
		}

		if (source == resetBT) {
			textPlat.setText("");
			comboMenu.setSelectedIndex(0);
			comboJenis.setSelectedIndex(0);
		}

		if (source == confirmAdminBT) {
			int targetHari = (Integer) comboHariAdmin.getSelectedItem() - 1;
			DatabaseParkir.hariIni = targetHari;

			DatabaseParkir.getWaktuSistem();
			String inputDetik = textDetik.getText().trim();

			if (!inputDetik.isEmpty()) {
				try {
					int detikBaru = Integer.parseInt(inputDetik);
					if (detikBaru > 0) {
						DatabaseParkir.waktuDipercepat = true;
						DatabaseParkir.detikPerJamSimulasi = detikBaru;
						DatabaseParkir.waktuRiilTerakhir = LocalDateTime.now();
						textLogLayanan
								.append("[SYSTEM] Simulasi Aktif: 1 Jam Sistem = " + detikBaru + " Detik Riil.\n");
					} else {
						JOptionPane.showMessageDialog(frame, "Input detik harus lebih besar dari 0!",
								"Error Konfigurasi", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Input detik harus berupa angka bulat!");
					return;
				}
			} else {
				DatabaseParkir.waktuDipercepat = false;
			}

			perbaruiTampilanTabelHarian();
			perbaruiTampilanTabelBulanan();
			JOptionPane.showMessageDialog(frame, "Konfigurasi Admin Berhasil Diperbarui!");
		}

		if (source == resetAdminBT) {
			comboHariAdmin.setSelectedIndex(DatabaseParkir.hariIni);
			textDetik.setText("");
		}
		textLogLayanan.setCaretPosition(textLogLayanan.getDocument().getLength());
	}

	public void perbaruiTampilanTabelBulanan() {
		if (modelTabelBulanan == null)
			return;
		modelTabelBulanan.setRowCount(0);
		double grandTotal = 0.0;

		try {
			Connection conn = Koneksi.getConnection();
			if (conn != null) {
				String sql = "SELECT * FROM riwayat_bulanan ORDER BY hari_ke ASC";
				PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					int hariKe = rs.getInt("hari_ke");
					double total = rs.getDouble("total_pendapatan");

					modelTabelBulanan.addRow(new Object[] { "Hari Ke-" + hariKe, "Rp " + total });
					grandTotal += total;
				}
				rs.close();
				pst.close();
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println("Gagal memuat riwayat bulanan: " + ex.getMessage());
		}

		if (lblGrandTotal != null) {
			lblGrandTotal.setText("Grand Total Pendapatan Bulanan: Rp " + grandTotal);
		}
	}

	public void perbaruiTampilanTabelHarian() {
		if (modelTabelHarian == null)
			return;
		modelTabelHarian.setRowCount(0);

		try {
			Connection conn = Koneksi.getConnection();
			if (conn != null) {
				int hariTerpilih = comboHariHarian.getSelectedIndex() + 1;

				String sql = "SELECT id_kendaraan, plat_nomor, jenis_kendaraan, waktu_masuk, waktu_keluar, tarif "
						+ "FROM riwayat_harian WHERE hari_ke = ? ORDER BY id_kendaraan DESC";

				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setInt(1, hariTerpilih);

				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					int id = rs.getInt("id_kendaraan");
					String plat = rs.getString("plat_nomor");
					String jenis = rs.getString("jenis_kendaraan");

					Timestamp masukTs = rs.getTimestamp("waktu_masuk");
					Timestamp keluarTs = rs.getTimestamp("waktu_keluar");
					String strMasuk = (masukTs != null) ? masukTs.toLocalDateTime().format(DatabaseParkir.formatWaktu)
							: "-";
					String strKeluar = (keluarTs != null)
							? keluarTs.toLocalDateTime().format(DatabaseParkir.formatWaktu)
							: "-";

					double tarif = rs.getDouble("tarif");
					String strTarif = String.format("Rp %.0f", tarif);

					modelTabelHarian.addRow(new Object[] { id, plat, jenis, strMasuk, strKeluar, strTarif });
				}
				rs.close();
				pst.close();
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println("Gagal memuat riwayat harian: " + ex.getMessage());
		}
	}

	private void perbaruiTabelDenah(DefaultTableModel model, String jenisFilter) {
		model.setRowCount(0);
		int kapasitasMaksimal = jenisFilter.equals("Mobil") ? 20 : 50;

		for (int i = 1; i <= kapasitasMaksimal; i++) {
			String status = "Kosong";
			String jenisKendaraan = " - ";
			String plat = " - ";

			if (jenisFilter.equals("Mobil")) {
				for (Mobil m : DatabaseParkir.slotMobil) {
					if (m.getLokasiParkir() == i) {
						status = "Terisi";
						jenisKendaraan = m.getJenis().getNamaJenis();
						plat = m.getPlatNomor();
						break;
					}
				}
			} else {
				for (Motor m : DatabaseParkir.slotMotor) {
					if (m.getLokasiParkir() == i) {
						status = "Terisi";
						jenisKendaraan = m.getJenis().getNamaJenis();
						plat = m.getPlatNomor();
						break;
					}
				}
			}

			Object[] baris = { (jenisFilter.equals("Mobil") ? "C-" : "B-") + i, status, jenisKendaraan, plat };
			model.addRow(baris);
		}
	}

//    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new MainGUI("admin")); }
}