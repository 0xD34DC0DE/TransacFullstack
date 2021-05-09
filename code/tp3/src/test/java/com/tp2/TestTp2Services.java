package com.tp2;

import com.tp2.model.Citoyen;
import com.tp2.model.Enfant;
import com.tp2.model.Permis;
import com.tp2.model.PermisTest;
import com.tp2.repository.CitoyenRepo;
import com.tp2.repository.EnfantRepo;
import com.tp2.repository.PermisRepo;
import com.tp2.service.CitoyenService;
import com.tp2.service.EmailService;
import com.tp2.service.EnfantService;
import com.tp2.service.PermisService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ComponentScan(basePackages = {"com.tp2.service"})
class TestTp2Services {

    @Autowired
    CitoyenService citoyenService;

    @Autowired
    CitoyenRepo citoyenRepo;

    @Autowired
    EnfantService enfantService;

    @Autowired
    EnfantRepo enfantRepo;

    @Autowired
    PermisService permisService;

    @Autowired
    PermisRepo permisRepo;

    @Autowired
    EmailService emailService;

    private final Citoyen citoyen1 = new Citoyen("380975760",
            "toto10",
            "totoPwd",
            "Toto",
            "deschamps",
            "M",
            19,
            "toto10@gmail.com",
            "450-111-2222",
            "Montreal");
    private final Citoyen citoyenTest = new Citoyen("614305554",
            "toto15",
            "totoPwd",
            "Toto",
            "deschamps",
            "M",
            19,
            "toto10@gmail.com",
            "450-111-2222",
            "Montreal");
    private final Citoyen citoyen2 = new Citoyen("949633365",
            "tutu99",
            "tutuPwd",
            "Tutu",
            "laroche",
            "M",
            27,
            "tutu99@gmail.com",
            "774-274-1174",
            "Valcartier");
    private final Citoyen citoyenDuplicateNas = new Citoyen(citoyen1.getNumeroAssuranceSocial(),
            "titi10",
            "titiPwd",
            "Titi",
            "lemoulin",
            "M",
            65,
            "titi10@gmail.com",
            "563-566-2314",
            "Quebec");
    private final Citoyen citoyenMinisterInvalid = new Citoyen("111111111",
            "tata56",
            "tataPwd123",
            "INVALID",
            "desroches",
            "F",
            24,
            "tata56@gmail.com",
            "445-143-1153",
            "Chicoutimi");
    private final Citoyen citoyenNullId = new Citoyen("36773452",
            "tyty46",
            "tytyPwd46",
            "tyty",
            "lefort",
            "F",
            44,
            "tyty46@gmail.com",
            "266-267-2886",
            "Montreal");

    private final Enfant enfant1ofCitoyen1 = new Enfant("211394391",
            "Titi",
            "longchamp",
            "M",
            7,
            "titi150@gmail.com",
            "470-145-2662",
            "Laval",
            citoyen1);
    private final Enfant enfantOfCitoyenTest = new Enfant("554999972",
            "Tito",
            "longchamp",
            "M",
            7,
            "titi150@gmail.com",
            "470-145-2662",
            "Laval",
            citoyenTest);
    private final Enfant enfant2ofCitoyen1 = new Enfant("510247043",
            "Tata",
            "dupre",
            "M",
            8,
            "tata134@gmail.com",
            "745-266-2775",
            "trois-rivieres",
            citoyen1);
    private final Enfant enfant2ofCitoyen1DuplicateNas = new Enfant(enfant1ofCitoyen1.getNumeroAssuranceSocial(),
            "Tutu",
            "desmeules",
            "M",
            13,
            "tutu432@gmail.com",
            "470-162-7745",
            "Laval",
            citoyen1);
    private final Enfant enfant2ofCitoyen1InvalidMinister = new Enfant("88663752",
            "INVALID",
            "desmeules",
            "M",
            13,
            "tutu432@gmail.com",
            "470-162-7745",
            "Laval",
            citoyen1);
    private final Enfant enfant2ofCitoyen1NonExistentParent = new Enfant("99554342",
            "Tutu",
            "desmeules",
            "M",
            13,
            "tutu432@gmail.com",
            "470-162-7745",
            "Laval",
            citoyen2);

    TestTp2Services() throws Exception {
    }

    @BeforeEach
    public void insertData() {
        Citoyen citoyen = citoyenRepo.save(citoyen1);
        citoyen1.setId(citoyen.getId());
        enfant1ofCitoyen1.setId(enfantRepo.save(enfant1ofCitoyen1).getId());
    }

    @AfterEach
    public void removeData() {
        citoyenRepo.deleteByNumeroAssuranceSocial(citoyen1.getNumeroAssuranceSocial());
        enfantRepo.deleteEnfantByNumeroAssuranceSocial(enfant1ofCitoyen1.getNumeroAssuranceSocial());
    }

    @Test
    public void testCitoyenRegister() {
        // Test for null citoyen registering
        assertThrows(Exception.class, () -> {
            citoyenService.register(null);
        });

        // Test for minister's invalid citoyen registering
        assertThrows(Exception.class, () -> {
            citoyenService.register(citoyenMinisterInvalid);
        });

        // Test for duplicate NAS registering
        assertThrows(Exception.class, () -> {
            citoyenService.register(citoyenDuplicateNas);
        });

        // Test for duplicate login registering
        assertThrows(Exception.class, () -> {
            citoyenService.register(citoyen1);
        });

        // Test valid registration
        assertDoesNotThrow(() -> {
            Citoyen result = citoyenService.register(citoyen2);
            assertEquals(citoyen2.getNumeroAssuranceSocial(), result.getNumeroAssuranceSocial());
            assertEquals(citoyen2.getLogin(), result.getLogin());
            assertEquals(citoyen2.getPassword(), result.getPassword());
            assertEquals(citoyen2.getNom(), result.getNom());
            assertEquals(citoyen2.getPrenom(), result.getPrenom());
            assertEquals(citoyen2.getSexe(), result.getSexe());
            assertEquals(citoyen2.getAge(), result.getAge());
            assertEquals(citoyen2.getCourriel(), result.getCourriel());
            assertEquals(citoyen2.getNumeroTelephone(), result.getNumeroTelephone());
            assertEquals(citoyen2.getVilleResidence(), result.getVilleResidence());
        });

        citoyenRepo.deleteByNumeroAssuranceSocial(citoyen2.getNumeroAssuranceSocial());
    }

    @Test
    public void testCitoyenLogin() {
        // Test for null login
        assertThrows(Exception.class, () -> {
            citoyenService.login(null, citoyen1.getPassword());
        });

        // Test for null password
        assertThrows(Exception.class, () -> {
            citoyenService.login(citoyen1.getLogin(), null);
        });

        // Test for null login and password
        assertThrows(Exception.class, () -> {
            citoyenService.login(null, null);
        });

        // Test for non existant login with correct password
        assertThrows(Exception.class, () -> {
            citoyenService.login("NON_EXISTENT", citoyen1.getPassword());
        });

        // Test for existent login with incorrect password
        assertThrows(Exception.class, () -> {
            citoyenService.login(citoyen1.getLogin(), "INVALID_PASSWORD");
        });

        // Test for valid login
        assertDoesNotThrow(() -> {
            assertNotNull(citoyenService.login(citoyen1.getLogin(), citoyen1.getPassword()));
        });
    }

    @Test
    public void testEnfantRegister() {

        // Test for null enfant registering
        assertThrows(Exception.class, () -> {
            enfantService.register(null);
        });

        // Test for duplicate NAS registering
        assertThrows(Exception.class, () -> {
            enfantService.register(enfant2ofCitoyen1DuplicateNas);
        });

        // Test for minister's invalid enfant registering
        assertThrows(Exception.class, () -> {
            enfantService.register(enfant2ofCitoyen1InvalidMinister);
        });

        // Test for non existent parent when registering
        assertThrows(Exception.class, () -> {
            enfantService.register(enfant2ofCitoyen1NonExistentParent);
        });

        assertDoesNotThrow(() -> {
            Enfant result = enfantService.register(enfant2ofCitoyen1);
            assertEquals(enfant2ofCitoyen1.getNom(), result.getNom());
            assertEquals(enfant2ofCitoyen1.getPrenom(), result.getPrenom());
            assertEquals(enfant2ofCitoyen1.getSexe(), result.getSexe());
            assertEquals(enfant2ofCitoyen1.getAge(), result.getAge());
            assertEquals(enfant2ofCitoyen1.getCourriel(), result.getCourriel());
            assertEquals(enfant2ofCitoyen1.getNumeroTelephone(), result.getNumeroTelephone());
            assertEquals(enfant2ofCitoyen1.getVilleResidence(), result.getVilleResidence());
            assertEquals(enfant2ofCitoyen1.getParent().getNumeroAssuranceSocial(), citoyen1.getNumeroAssuranceSocial());
        });

        enfantRepo.deleteEnfantByNumeroAssuranceSocial(enfant2ofCitoyen1.getNumeroAssuranceSocial());
    }

    @Test
    public void testGetEnfantsFromCitoyen() {
        // Test for null citoyen enfants query
        assertThrows(Exception.class, () -> {
            enfantService.getEnfantsFromCitoyen(null);
        });

        // Test for non existant citoyen enfants query
        assertThrows(Exception.class, () -> {
            enfantService.getEnfantsFromCitoyen(citoyen2.getNumeroAssuranceSocial());
        });

        citoyenRepo.save(citoyen2);

        // Test for valid citoyen without childs
        assertDoesNotThrow(() -> {
            List<Enfant> enfantList = enfantService.getEnfantsFromCitoyen(citoyen2.getNumeroAssuranceSocial());
            assertEquals(0, enfantList.size());
        });

        // Test for valid citoyen with childs
        assertDoesNotThrow(() -> {
            List<Enfant> enfantList = enfantService.getEnfantsFromCitoyen(citoyen1.getNumeroAssuranceSocial());
            assertEquals(1, enfantList.size());
        });

        citoyenRepo.deleteByNumeroAssuranceSocial(citoyen2.getNumeroAssuranceSocial());
    }

    @Test
    public void testCreatePermisVaccin() {
        // Test for null citoyen permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisVaccin(null);
        });

        // Test for null id citoyen permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisVaccin(citoyenNullId);
        });

        // Test for nonexistent citoyen permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisVaccin(citoyen2);
        });

        // Test for nonexistent enfant permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisVaccin(enfant2ofCitoyen1);
        });

        // Test for valid citoyen permis creation
        assertDoesNotThrow(() -> {
            permisService.createPermisVaccin(citoyen1);
        });

        // Test for valid enfant permis creation
        assertDoesNotThrow(() -> {
            permisService.createPermisVaccin(enfant1ofCitoyen1);
        });

        permisRepo.deleteAllByCitoyenNumeroAssuranceSocial(citoyen1.getNumeroAssuranceSocial());
        permisRepo.deleteAllByCitoyenNumeroAssuranceSocial(enfant1ofCitoyen1.getNumeroAssuranceSocial());
    }

    @Test
    public void testCreatePermisTest() {
        // Test for null citoyen permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisTest(null);
        });

        // Test for null id citoyen permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisTest(citoyenNullId);
        });

        // Test for nonexistent citoyen permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisTest(citoyen2);
        });

        // Test for nonexistent enfant permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisTest(enfant2ofCitoyen1);
        });

        // Test for citoyen not present in database permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisTest(citoyenTest);
        });

        citoyenRepo.save(citoyenTest);

        // Test for valid citoyen permis creation
        assertDoesNotThrow(() -> {
            permisService.createPermisTest(citoyenTest);
        });

        // Test for enfant not present in database permis creation
        assertThrows(Exception.class, () -> {
            permisService.createPermisTest(enfantOfCitoyenTest);
        });

        citoyenRepo.save(enfantOfCitoyenTest);

        // Test for valid enfant permis creation
        assertDoesNotThrow(() -> {
            permisService.createPermisTest(enfantOfCitoyenTest);
        });



        permisRepo.deleteAllByCitoyenNumeroAssuranceSocial(citoyenTest.getNumeroAssuranceSocial());
        permisRepo.deleteAllByCitoyenNumeroAssuranceSocial(enfantOfCitoyenTest.getNumeroAssuranceSocial());
        citoyenRepo.deleteByNumeroAssuranceSocial(citoyenTest.getNumeroAssuranceSocial());
        enfantRepo.deleteEnfantByNumeroAssuranceSocial(enfantOfCitoyenTest.getNumeroAssuranceSocial());
    }

    @Test
    public void testPermisValid() throws Exception {
        // Test for null hash permis validation
        assertThrows(Exception.class, () -> {
            permisService.isPermisValidByHash(null);
        });

        // Test for non existent hash permis validation
        assertDoesNotThrow(() -> {
            assertFalse(permisService.isPermisValidByHash("nonExistentHash"));
        });

        Permis permisVaccinOfCitoyen1 = permisRepo.save(new Permis(citoyen1));
        permisVaccinOfCitoyen1.generateHash();

        // Test for existent hash permis validation
        assertDoesNotThrow(() -> {
            assertTrue(permisService.isPermisValidByHash(permisVaccinOfCitoyen1.getPermisHash()));
        });

        permisRepo.deleteAllByCitoyenNumeroAssuranceSocial(citoyen1.getNumeroAssuranceSocial());
    }

    @Test
    public void testPermisTestValid() throws Exception {
        // Test for null hash permis validation
        assertThrows(Exception.class, () -> {
            permisService.isPermisValidByHash(null);
        });

        // Test for non existent hash permis validation
        assertDoesNotThrow(() -> {
            assertFalse(permisService.isPermisValidByHash("nonExistentHash"));
        });

        PermisTest permisTestOfCitoyen1 = permisRepo.save(new PermisTest(citoyen1));
        permisTestOfCitoyen1.generateHash();
        System.out.println(permisTestOfCitoyen1.getPermisHash());

        // Test for existent hash permis validation
        assertDoesNotThrow(() -> {
            assertTrue(permisService.isPermisValidByHash(permisTestOfCitoyen1.getPermisHash()));
        });

        // Create PermisTest with an expired date
        PermisTest permisTestExpiredOfCitoyen1 = permisRepo.save(
                new PermisTest(citoyen1,
                        new Date(new Date().getTime() - Duration.ofDays(PermisTest.validDurationDays + 1).toMillis())
                )
        );
        permisTestExpiredOfCitoyen1.generateHash();

        // Test for existent expired hash permis validation
        assertDoesNotThrow(() -> {
            assertFalse(permisService.isPermisValidByHash(permisTestExpiredOfCitoyen1.getPermisHash()));
        });

        permisRepo.deleteAllByCitoyenNumeroAssuranceSocial(citoyen1.getNumeroAssuranceSocial());
    }

    @Test
    public void testQRCodeEmail() throws Exception {
//        Permis permisOfCitoyen1 = permisRepo.save(new Permis(citoyen1));
//        permisOfCitoyen1.generateHash();
//        emailService.SendPDFQRImageMail(permisOfCitoyen1, "emerickpoulin31415@gmail.com");
    }


}
