package org.example.init;

import jakarta.annotation.PostConstruct;
import org.example.models.*;
import org.example.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    public DataInitializer(UserRepository userRepository, TeamRepository teamRepository,
                           EventRepository eventRepository, AchievementRepository achievementRepository,
                           PasswordEncoder passwordEncoder, UserEventRepository userEventRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.eventRepository = eventRepository;
        this.achievementRepository = achievementRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEventRepository = userEventRepository;
    }

    @PostConstruct
    public void init() {
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
        user.setName("Пользователь");
        user.setNickname("user01");
        user.setPhone("9990000003");
        user.setPassword(passwordEncoder.encode("userpass"));
        user.setRole(Role.USER);
        user.setPoints(20);
        user.setEventCount(2);
        user.setAvatarUri("img/user.png");
        user.setTeam(красные);
        userRepository.save(user);

        // Событие
        Event event = new Event();
        event.setTitle("Субботник в парке");
        event.setDescription("Уборка территории, покраска скамеек и посадка деревьев");
        event.setLocationName("Парк Победы");
        event.setLatitude(55.751244);
        event.setLongitude(37.618423);
        event.setDateTime("2025-05-19T10:00");
        event.setCreator(organizer);
        event.setFavorite(false);
        event.setCompleted(true);
        event.setVerified(false);
        event.setRejected(false);
        event.setParticipantCount(3);
        event.setConfirmationComment("Чисто");
        event.setImageUri(List.of("img/clean1.jpg", "img/clean2.jpg", "img/clean3.jpg"));
        event.setTeams(Set.of(красные));
        eventRepository.save(event);

        UserEventCrossRef ref = new UserEventCrossRef(user, event, LocalDateTime.now());
        userEventRepository.save(ref);

        // Достижение
        Achievement achievement = new Achievement();
        achievement.setTitle("Первый шаг");
        achievement.setDescription("Участвуй в первом субботнике");
        achievement.setImageResId(1);
        achievementRepository.save(achievement);
    }
}
