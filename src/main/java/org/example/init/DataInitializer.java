package org.example.init;

import jakarta.annotation.PostConstruct;
import org.example.models.*;
import org.example.repositories.*;
import org.example.services.AchievementService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final EventRepository eventRepository;
    private final AchievementRepository achievementRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventRepository userEventRepository;
    private final AchievementService achievementService;

    public DataInitializer(UserRepository userRepository, TeamRepository teamRepository,
                           EventRepository eventRepository, AchievementRepository achievementRepository,
                           PasswordEncoder passwordEncoder, UserEventRepository userEventRepository, AchievementService achievementService) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.eventRepository = eventRepository;
        this.achievementRepository = achievementRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEventRepository = userEventRepository;
        this.achievementService = achievementService;
    }

    @PostConstruct
    public void init() {

        // Достижения
        Achievement a1 = new Achievement("Добро пожаловать", "Вы зарегистрировались на платформе", 1, Collections.emptySet());
        Achievement a2 = new Achievement("Первое участие", "Участвуй в первом субботнике", 2, Collections.emptySet());
        Achievement a3 = new Achievement("Постоянный участник", "Прими участие в 5 субботниках", 3, Collections.emptySet());
        Achievement a4 = new Achievement("Присоединился к команде", "Вступи в команду", 4, Collections.emptySet());
        Achievement a5 = new Achievement("Твой первый ивент", "Создай первое событие", 5, Collections.emptySet());
        Achievement a6 = new Achievement("Организатор по жизни", "Создай 5 событий", 6, Collections.emptySet());
        Achievement a7 = new Achievement("Первая сотня", "Набери 100 очков", 7, Collections.emptySet());

        achievementRepository.saveAll(List.of(a1, a2, a3, a4, a5, a6, a7));

        achievementRepository.findAll().forEach(a ->
                System.out.println("Achievement saved: " + a.getTitle() + " | ID: " + a.getId())
        );

        // Команда
        Team красные = new Team("Красные", 1727987712, "[[55.52830844921757, 37.511560521192806], [55.52855491609576, 37.511380813188794], [55.52867662756232, 37.511418364115], [55.52884702298009, 37.51159270770099], [55.52899611836258, 37.51227130658174], [55.5289383059347, 37.51252879864718], [55.52867358478027, 37.51271655327821], [55.52862490023523, 37.51288016802811], [55.52837995519995, 37.51304646498706], [55.528287149537995, 37.51303305394197], [55.52813653004482, 37.512882850237155], [55.528060459373584, 37.51258244282747], [55.528054373713495, 37.51234372622515], [55.52815935121738, 37.512158653803134]]");
        teamRepository.save(красные);

        Team синие = new Team("Синие", 1711276287, "[[55.52797892995888, 37.51354621573619], [55.52819344931915, 37.513653504096794], [55.52830755488318, 37.51377688571148], [55.52845513091966, 37.51434283181362], [55.52835928262935, 37.51483367606334], [55.527899406555925, 37.51473489121221], [55.527663585566536, 37.514541772163135], [55.527566213905175, 37.51412871197484], [55.52766510699683, 37.513715651786534], [55.527730528443335, 37.51362713888904]]");
        teamRepository.save(синие);

        Team зеленые = new Team("Зелёные", 1711341312, "[[55.52757941146031, 37.51496759253141], [55.52782284007785, 37.515074880892016], [55.52790195405282, 37.51524922447798], [55.528025188964726, 37.51585272150633], [55.52800845338216, 37.51602438288327], [55.52794607523869, 37.51626846390363], [55.52748812533868, 37.51614240007993], [55.52731163823361, 37.516029747301324], [55.527211222801654, 37.51582589941617], [55.527157886403515, 37.515523200340255], [55.527261344864215, 37.51512623340604], [55.52733589564443, 37.51503235609053]]");
        teamRepository.save(зеленые);

        Team желтые = new Team("Жёлтые", 1728052992, "[[55.52920975752816, 37.51210637997134], [55.529410577887575, 37.5119347185944], [55.52966008053614, 37.51197763393863], [55.52980308743519, 37.51241751621708], [55.529906538909, 37.512835940823386], [55.52987611203331, 37.51299687336428], [55.52983655705956, 37.51307733963473], [55.529562713838004, 37.51326509426578]]");
        teamRepository.save(желтые);

        Team фиолетовые = new Team("Фиолетовые", 1727987967, "[[55.530058672932924, 37.51265891502842], [55.530320342071725, 37.51247116039737], [55.53056983892473, 37.51261599968418], [55.53076760969111, 37.51338311146244], [55.53071284250195, 37.51365133236391], [55.530434935277725, 37.513848871223956]]");
        teamRepository.save(фиолетовые);

        Team оранжевые = new Team("Оранжевые", 1728029952, "[[55.53010127035373, 37.51655884693605], [55.52936493698722, 37.51717039059147], [55.52930408237068, 37.517454704747045], [55.52958705553524, 37.51852222393496], [55.52976048969007, 37.51862414787753], [55.53011039836644, 37.5185812325333], [55.53020167837659, 37.518935284123245], [55.530329470033394, 37.51901575039369], [55.53107187045852, 37.51853295277101], [55.53112359455389, 37.51825400303346], [55.53085888816952, 37.517202577099624], [55.53059417999602, 37.517063102230864], [55.5304085789453, 37.51721867035373], [55.53019863571301, 37.51698800037844]]");
        teamRepository.save(оранжевые);

        Team бирюзовые = new Team("Бирюзовые", 1711341567, "[[55.532182944094586, 37.5141611791754], [55.531650501306245, 37.51645178567412], [55.53193650004672, 37.517567584624324], [55.532076456116044, 37.5176373220587], [55.532878562189566, 37.516912345692695], [55.53279254029454, 37.5165441645828], [55.532868601775085, 37.51632422344358], [55.53333713723743, 37.516002358361796], [55.53341928248767, 37.5157341374603], [55.533245864535054, 37.515036763116434], [55.53294770555814, 37.51510650055083], [55.53277428551721, 37.51415163414152], [55.53243657060199, 37.5139853371826]]");
        teamRepository.save(бирюзовые);

        Team фиоловые = new Team("Фиоловые", 1721316300, "[[55.5309340581643, 37.51329050547962], [55.53013080179474, 37.515430908273494], [55.53033161743163, 37.51622484214191], [55.53042898221827, 37.51628385074025], [55.531576042886975, 37.51547382361776], [55.53192289281327, 37.51402006633166], [55.53176468094723, 37.51334951407796], [55.531478680951984, 37.513220768045244], [55.53119876404059, 37.51341925151235], [55.531007080653715, 37.51317785270101]]");
        teamRepository.save(фиоловые);

        Team салатовые = new Team("Салатовые", 1721368422, "[[55.52862600762548, 37.51574223899504], [55.52895462712072, 37.51597827338835], [55.529111660104505, 37.516573723789634], [55.529002121018294, 37.517067250248395], [55.52884998289031, 37.516981419559926], [55.52852136251708, 37.5170887079205], [55.528277938240784, 37.51694386863371], [55.52816535400134, 37.5165308084454], [55.528259681359074, 37.516101655003034], [55.528430078590766, 37.51598363780638]]");
        teamRepository.save(салатовые);

        // Админ
        User admin = new User();
        admin.setName("Админ");
        admin.setNickname("admin01");
        admin.setPhone("9990000001");
        admin.setPassword(passwordEncoder.encode("adminpass"));
        admin.setRole(Role.ADMIN);
        admin.setPoints(100);
        admin.setEventCount(0);
        admin.setAvatarUri("img/admin.png");
        userRepository.save(admin);

        // Организатор
        User organizer = new User();
        organizer.setName("Организатор");
        organizer.setNickname("org01");
        organizer.setPhone("9990000002");
        organizer.setPassword(passwordEncoder.encode("orgpass"));
        organizer.setRole(Role.ORGANIZER);
        organizer.setPoints(50);
        organizer.setEventCount(1);
        organizer.setAvatarUri("img/org.png");
        organizer.setTeam(красные);
        userRepository.save(organizer);

        // Пользователь
        User user = new User();
        user.setName("Вова");
        user.setNickname("kosma");
        user.setPhone("89771829402");
        user.setPassword(passwordEncoder.encode("vovkaplovka"));
        user.setRole(Role.USER);
        user.setPoints(10);
        user.setEventCount(1);
        user.setAvatarUri("img/user.png");
        user.setTeam(красные);
        user.setJoinedTeamAt(LocalDateTime.now());
        userRepository.save(user);

        User user2 = new User();
        user2.setName("Вадим");
        user2.setNickname("vadiks");
        user2.setPhone("89042283750");
        user2.setPassword(passwordEncoder.encode("vadiks2001"));
        user2.setRole(Role.USER);
        user2.setPoints(150);
        user2.setEventCount(5);
        user2.setAvatarUri("img/user.png");
        user2.setTeam(синие);
        user2.setJoinedTeamAt(LocalDateTime.now());
        userRepository.save(user2);

        User user3 = new User();
        user3.setName("Максим");
        user3.setNickname("makserh");
        user3.setPhone("89067734582");
        user3.setPassword(passwordEncoder.encode("makskvaks22"));
        user3.setRole(Role.USER);
        user3.setPoints(120);
        user3.setEventCount(3);
        user3.setAvatarUri("img/user.png");
        user3.setTeam(синие);
        user3.setJoinedTeamAt(LocalDateTime.now());
        userRepository.save(user3);

        User user4 = new User();
        user4.setName("Кирилл");
        user4.setNickname("absolute");
        user4.setPhone("89874659202");
        user4.setPassword(passwordEncoder.encode("Absolut228"));
        user4.setRole(Role.USER);
        user4.setPoints(140);
        user4.setEventCount(4);
        user4.setAvatarUri("img/user.png");
        user4.setTeam(красные);
        user4.setJoinedTeamAt(LocalDateTime.now());
        userRepository.save(user4);

        User user5 = new User();
        user5.setName("Ванек");
        user5.setNickname("scorpion34");
        user5.setPhone("89657483823");
        user5.setPassword(passwordEncoder.encode("kotkot34"));
        user5.setRole(Role.USER);
        user5.setPoints(90);
        user5.setEventCount(1);
        user5.setAvatarUri("img/user.png");
        user5.setTeam(зеленые);
        user5.setJoinedTeamAt(LocalDateTime.now());
        userRepository.save(user5);

        // Событие
        Event event = new Event();
        event.setTitle("Субботник в парке");
        event.setDescription("Уборка территории, покраска скамеек и посадка деревьев");
        event.setLocationName("Парк Победы");
        event.setLatitude(55.751244);
        event.setLongitude(37.618423);
        event.setDateTime("2025-05-19T10:00");
        event.setCreator(organizer);
        event.setFavorite(true);
        event.setCompleted(true);
        event.setVerified(false);
        event.setRejected(false);
        event.setParticipantCount(10);
        event.setConfirmationComment("Чисто");
        event.setImageUri(List.of("clean1.jpg", "clean2.jpg", "clean3.jpg"));
        event.setTeams(Set.of(зеленые));
        event.setCompletedAt(LocalDateTime.of(2025, 6, 10, 11, 0));
        eventRepository.save(event);

        Event event2 = new Event();
        event2.setTitle("Чистка набережной у Москва-реки");
        event2.setDescription("Сбор мусора вдоль берега, расчистка тропинок и установка урн");
        event2.setLocationName("Набережная Тараса Шевченко");
        event2.setLatitude(55.748933);
        event2.setLongitude(37.540569);
        event2.setDateTime("2025-06-04T09:30");
        event2.setCreator(organizer);
        event2.setFavorite(true);
        event2.setCompleted(true);
        event2.setVerified(true);
        event2.setRejected(false);
        event2.setParticipantCount(4);
        event2.setConfirmationComment("Работа проведена на отлично");
        event2.setImageUri(List.of("river1.jpg", "path2.jpg", "clean_area.jpg"));
        event2.setTeams(Set.of(красные, синие));
        event2.setCompletedAt(LocalDateTime.of(2025, 6, 3, 11, 0));
        eventRepository.save(event2);

        UserEventCrossRef testRef = new UserEventCrossRef(user5, event, LocalDateTime.now());
        userEventRepository.save(testRef);

        UserEventCrossRef ref = new UserEventCrossRef(user, event2, LocalDateTime.of(2025, 6, 4, 9, 0));
        userEventRepository.save(ref);
        UserEventCrossRef ref2 = new UserEventCrossRef(user2, event2, LocalDateTime.of(2025, 6, 4, 9, 0));
        userEventRepository.save(ref2);
        UserEventCrossRef ref3 = new UserEventCrossRef(user3, event2, LocalDateTime.of(2025, 6, 4, 9, 0));
        userEventRepository.save(ref3);
        UserEventCrossRef ref4 = new UserEventCrossRef(user4, event2, LocalDateTime.of(2025, 6, 4, 9, 0));
        userEventRepository.save(ref4);

        achievementService.checkAndAssign(admin);
        achievementService.checkAndAssign(organizer);
        achievementService.checkAndAssign(user);
        achievementService.checkAndAssign(user2);
        achievementService.checkAndAssign(user3);
        achievementService.checkAndAssign(user4);
        achievementService.checkAndAssign(user5);
    }
}
