package SpotOn;

/**
 *  KELOMPOK 2
 *  - NANANG SAEPUDIN   - 2510631170011
 *  - FITRIA            - 2510631170023
 *  - DIMAS INDRAWiJAYA - 2510631170032
 */

public class Motor extends Kendaraan {
	private double tarifPerJam;

	public Motor(String platNomor, int lokasiParkir) {
		super(platNomor, JenisKendaraan.MOTOR, lokasiParkir);
		this.tarifPerJam = 2000.0;
	}

	public double getTarifPerJam() {
		return tarifPerJam;
	}
}