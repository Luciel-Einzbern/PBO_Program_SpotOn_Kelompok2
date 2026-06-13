package SpotOn;

/**
 *  KELOMPOK 2
 *  - NANANG SAEPUDIN   - 2510631170011
 *  - FITRIA            - 2510631170023
 *  - DIMAS INDRAWiJAYA - 2510631170032
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseParkir {
	public static ArrayList<Mobil> slotMobil = new ArrayList<>();
	public static ArrayList<Motor> slotMotor = new ArrayList<>();

	public static int hariIni = 0;
	public static boolean waktuDipercepat = false;
	public static int detikPerJamSimulasi = 3600;

	public static LocalDateTime waktuVirtualSaatIni = LocalDateTime.now();
	public static LocalDateTime waktuRiilTerakhir = LocalDateTime.now();
	public static DateTimeFormatter formatWaktu = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime getWaktuSistem() {
		LocalDateTime sekarang = LocalDateTime.now();
		long detikBerlalu = java.time.Duration.between(waktuRiilTerakhir, sekarang).getSeconds();

		if (detikBerlalu > 0) {
			waktuRiilTerakhir = waktuRiilTerakhir.plusSeconds(detikBerlalu);

			if (!waktuDipercepat) {
				waktuVirtualSaatIni = waktuVirtualSaatIni.plusSeconds(detikBerlalu);
			} else {
				long detikSimulasi = (detikBerlalu * 3600L) / detikPerJamSimulasi;
				waktuVirtualSaatIni = waktuVirtualSaatIni.plusSeconds(detikSimulasi);
			}
		}
		return waktuVirtualSaatIni.plusDays(hariIni);
	}
}