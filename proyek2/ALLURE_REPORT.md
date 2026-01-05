# Cara Menggunakan Allure Report

## Setup (Sudah Dilakukan)

✅ Dependency Allure sudah ditambahkan ke `pom.xml`:

- `allure-junit5` - Integrasi JUnit 5
- `allure-selenide` - Screenshot otomatis
- `aspectjweaver` - Aspect untuk annotation

## Cara Run Test & Generate Report

### 1. Run Test (Generate Allure Results)

```bash
mvn clean test -Dtest=AboutUsPageTest
```

Hasil test akan disimpan di: `target/allure-results/`

### 2. Generate & Open Report (Otomatis di Browser)

```bash
mvn allure:serve
```

Command ini akan:

- Generate HTML report dari `allure-results`
- Start local web server
- **Otomatis buka browser** dengan report

### 3. Generate Report Only (Tanpa Buka Browser)

```bash
mvn allure:report
```

Report HTML akan dibuat di: `target/site/allure-maven-plugin/`

Untuk membuka manual:

```bash
mvn allure:open
```

## Allure Annotations yang Digunakan

### Class Level:

```java
@Epic("GLPI Website Testing")      // Kategori besar
@Feature("About Us Page")           // Fitur yang ditest
```

### Method Level:

```java
@Test
@DisplayName("Check About Us headline is visible")
@Description("Verify that the About Us headline is visible on the page")
@Severity(SeverityLevel.CRITICAL)  // BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL
@Story("Headline Verification")     // Sub-fitur
public void testHeadlineVisible() {
    // test code
}
```

## Keuntungan Allure Report

### 1. **Tanggal & Waktu Eksekusi Otomatis**

✅ Setiap report include timestamp kapan test dijalankan
✅ TIDAK BISA DIAWUR - timestamp dari system

### 2. **Informasi Detail:**

- ✅ Total tests, passed, failed
- ✅ Durasi setiap test
- ✅ Test history & trends
- ✅ Category (Epic, Feature, Story)
- ✅ Severity level

### 3. **Screenshot Otomatis:**

- Tangkap screenshot saat test fail
- Screenshot disimpan di report

### 4. **Rich Visualization:**

- Pie chart: Pass/Fail ratio
- Timeline: Kapan test dijalankan
- Graph: Test duration
- Suites: Grouping by feature

### 5. **Export & Share:**

- Report HTML bisa di-zip dan share
- Bisa host di web server
- Bisa integrate dengan CI/CD (Jenkins, GitHub Actions)

## Contoh Output Report

Report akan menampilkan:

```
Overview Dashboard:
├── Total Tests: 2
├── Passed: 2
├── Failed: 0
├── Duration: 26.55s
└── Timestamp: 2026-01-05 21:37:09

Test Cases:
├── TC-ABT-001: Check About Us headline is visible
│   ├── Status: PASSED
│   ├── Duration: 10.2s
│   ├── Severity: CRITICAL
│   └── Story: Headline Verification
│
└── TC-ABT-002: Check Discover our Jobs button
    ├── Status: PASSED
    ├── Duration: 16.3s
    ├── Severity: NORMAL
    └── Story: Button Navigation
```

## Tips Penggunaan

### 1. Run Semua Test:

```bash
mvn clean test
mvn allure:serve
```

### 2. Run Specific Test:

```bash
mvn clean test -Dtest=AboutUsPageTest
mvn allure:serve
```

### 3. Run Multiple Tests:

```bash
mvn clean test -Dtest=AboutUsPageTest,AdministrationPageTest
mvn allure:serve
```

### 4. Clean Old Results:

```bash
mvn clean
```

## File Structure

```
proyek2/
├── pom.xml                          # Allure config
├── target/
│   ├── allure-results/             # Raw test results (JSON)
│   │   ├── *-result.json
│   │   ├── *-container.json
│   │   └── executorInfo.json
│   └── site/
│       └── allure-maven-plugin/    # Generated HTML report
│           ├── index.html
│           ├── data/
│           └── plugins/
└── .allure/                        # Allure installation
```

## Dokumentasi Test Case untuk Laporan

Gunakan Allure report sebagai bukti eksekusi test:

1. ✅ Screenshot dashboard (Overview)
2. ✅ Screenshot individual test results
3. ✅ Export report sebagai ZIP
4. ✅ Timestamp otomatis (TIDAK BISA DIPALSUKAN)

## Troubleshooting

### Error: "Allure command not found"

Solution: `mvn allure:serve` akan otomatis download Allure

### Error: "No test results found"

Solution: Run test dulu dengan `mvn test`

### Browser tidak terbuka otomatis

Solution: Buka manual `target/site/allure-maven-plugin/index.html`

## Next Steps

Untuk page lain (HomePage, TrainingPage, dll):

1. Tambahkan Allure annotations seperti di `AboutUsPageTest`
2. Run test: `mvn clean test`
3. Generate report: `mvn allure:serve`
4. Screenshot untuk dokumentasi

---

**PENTING:**

- Allure report **TIDAK BISA DIAWUR** karena timestamp dari system
- Report bisa dijadikan bukti formal untuk dokumentasi project
- Bisa di-export dan di-attach ke laporan akhir
