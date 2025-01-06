public class Jdbc_Jpa_difference {
    public static void main(String[] args) {
        System.out.println("jdbc와 jpa의 설명 및 예시");
        System.out.println("jdbc와 data jdbc의 차이");
        System.out.println("jpa와 data jpa의 차이");
        System.out.println("jdbc와 jpa는 결국 디비에서 데이터를 불러오는 방식의 차이이다.");
    }

    // jdbc로 db에 연결하기 위해 디비 정보를 입력해주고 connection을 가져올 수 있는 나머지 정보 생성
    /*
    public class JdbcConnector {
        private static final String URL = "jdbc:mysql://localhost:3306/chess";
        private static final String USER = "root";
        // 실제 비밀번호를 쳐야 함
        private static final String PASSWORD = "password";

        public static Connection getConnection() {
            Connection connection = null;
            try{
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
            } catch (SQLException e){
                e.printStackTrace();
            }
            return connection;
        }
    }
     */


    /*
     1. 순수 Jdbc란?
     Java DataBase Connectivity의 약자로 데이버테이를 연결하기 위한 API 이다.
     Connection을 열어주고 닫고, 결과를 ResultSet에 저장하는 등 일일이 처리해줘야 하는 부분이 많다.
     */
    /*
    public class JdbcMemberRepository {
        private final DataSource dataSource;
        public JdbcMemberRepository(DataSource dataSource){
            this.dataSource = dataSource;
        }

        public Member save(Member member) {
            // insert 쿼리문 / name이라는 열에 ?라는 값을 넣을 거다.
            String sql = "insert into member(name) values(?)";

            // Connection / 자바에서 DB와 연결하기 위해 사용하는 객체
            Connection conn = null;

            PreparedStatement pstmt = null;

            ResultSet rs = null; // 결과를 저장하는 객체

            try{
                conn = getConnection();
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                // 위 쿼리문에서 ?에 넣을 값을 세팅해준다.
                pstmt. setString(1, member.getName());

                // 디비에 쿼리문을 보내준다.
                pstmt.executeUpdate();

                // key를 꺼내준다.
                rs = pstmt.getGeneratedKeys();

                // 만약 rs에 값이 있다면
                if(rs.next()) {
                    member.setId(rs.getLong(1));
                } else {
                    // 만약 rs에 값이 없다면
                    throw new SQLException("id 조회 실패");
                }
                return member;
            } catch (Exception e) { // 예외
                throw new IllegalStateException(e);
            } finally { // 항상 connection은 닫아준다.
                close(conn,pstmt,rs);
            }
        }
        return member;
    }
    */
    /*
     2. JdbcTemplate란?
     순수 JDBC와 동일한 환경설정을 하면 된다.
     스프링 JdbcTemplate과 MyBatis 같은 라이브러리는 순수 JDBC API에서 본 반복 코드를 대부분 제거해준다.
     하지만 SQL은 직접 작성해야 한다.

     JdbcTemplate은 MyBatis와 같은 라이브러리다.
    */
    /*
    public class JdbcTemplateMemberRepository {
        private final JdbcTemplate jdbcTemplate;
        public JdbcTemplateMemberRepository(DataSource dataSource) {
            jdbcTemplate = new JdbcTemplate(dataSource);
        }

        public Member save(Member member) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("member").usingGenerateKeycolumns("id");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", member.getName());

            Number key = jdbcInsert.excuteAndRetrunKey(new MapSqlParameterSource(parameters));
            member.setId(key.longValue());

            return member;
        }

        public Optional<Member> findById(Long id) {
            List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
            return result.stream().findAny();
        }
    }
    */

    /*
     3. JPA란 "Java Persistence API"의 약자로, 자바 언어를 위한 데이터 영속성 프레임워크이다.
     전세계적으로 봤을 때 대부분이 JPA를 사용하고 있고, 국내에서도 증가하고 있는 추세이다.
     JPA는 자바 애플리케이션에서 관계형 데이터베이스(RDBMS MYSQL)를 사용하는데 도움이 되는 기술로, 객체 지향 프로그래밍과 데이터베이스 간의 매핑을 간소화하고 표준화하는 목적을 가지고 있다.
     JPA는 객체와 데이터베이스 간의 매핑을 위한 주요 어노테이션 및 기타 기능을 제공하여 개발자가 더 효율적으로 데이터베이스를 다룰 수 있도록 도와준다.
     - JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
     - JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임 전환을 할 수 있다.
     - JPA를 사용하면 개발 생산성을 크게 높일 수 있다.

     사용법으로는 엔티티에 @Entity를 선언하여 JPA가 관리하는 엔티티임을 표시해준다.
     리포지토리에는 EntityManager객체를 생성하여 EntityManager가 관리하게 한다.
    */
    /*
    public class JpaMemberRepository {
        private final EntityManager em; //JPA는 EntityManager가 관리한다.
        public JpaMemberRepository(EntityManager em) {
            this.em = em;
        }

        public Memebr save(Member member) {
            em.persist(member);
            return member;
        }

        public Optional<Member> findByID(Long id) {
            Member member = em.find(Member.class,id);
            return Optional.ofNullable(member);
        }
        public List<Member> findAll(){
            return em.createQuery("select m from Member m", Member.class).getResultList(); // jpql 생성가능
        }

        public Optional<Member> findByName(String name) {
            List<Member> result = em.createQuery("select m from Member m where m.name=:name", Member.class) // jpql 생성가능
                    .setParameter("name", name)
                    .getResultList();
            return result.stream().findAny();
        }
    }
     */

    /*
    4. Spring Data Jpa란 스프링 부트와 JPA만 사용해도 개발 생산성이 정말 많이 증가하고, 개발해야할 코드도 확연히 줄어든다.
     여기에 스프링 데이터 JPA를 사용하면 라포지토리에 구현 클래스 없이 인터페이스 만으로 개발을 완료할 수 있다.
     또한 반복 개발해온 기본 CRUD 기능도 스프링 데이터 JPA가 모두 제공한다.
     개발자는 핵심 비즈니스 로직을 개발하는데 집중할 수 있게 된다.
     하지만 JPA를 정확히 알고 사용하는 것이 좋아보인다.

     사용법은 앞의 JPA설정을 그대로 사용한다. EX) @Entity, EntityManager생성 등
     추가로 Repository에 extends JpaRepository<Member,Long>를 해주면 기능 구현이 끝난다.
     만약 findByName이라는 name으로 객체를 찾고싶은 함수를 만들고 싶다면
     @Override
     Optional<Member> findByName(String name);
     라고 두 줄만 적어주면 자동으로 JPQL이 생성된다. -> select m from Member m where m.name=?

     */
    /*
    @Configuration
    public class SpringConfig{
        private final MemberRepository memberRepository;
        public SpringConfig(MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }

        @Bean
        public MemberService memberService(){
            return new MemberService(memberRepository);
        }
    }
     */


    // 기존의 jdbc와 jpa의 차이점
    /* JDBC 사용 방식
    저수준 API: JDBC는 데이터베이스와의 연결, 쿼리 실행, 결과 처리 등을 직접 관리해야 하는 저수준 API이다. SQL문을 직접 작성하고 ResultSet으로 결과를 받아 처리해야 한다.

    반복적인 코드: 데이터베이스 연결 및 자원 관리, 예외 처리 등 반복적인 코드가 많이 필요하다.
                 이로 인해 코드 양이 많아지고 유지보수가 어려울 수 있다.

    성능 최적화: JDBC를 사용하면 쿼리를 직접 작성하고 최적화 해야 합니다. 이로 인해 데이터베이스에 대한 성능 튜닝이 필요할 수 있습니다.
     */

    /* JAP 사용 방식
    객체 지향적: JPA는 객체 지향 프로그래밍의 관점에서 데이텅베이스를 다룰 수 있도록 한다.
               엔티티 클래스를 생성하고 어노테이션으로 매핑하면 SQL을 직접 작성하지 않고도 데이터베이스 연산이 가능하다.

    높은 생산성: JPA를 사용하면 반복적인 CRUD 작업을 자동으로 처리할 수 있습니다.
               데이터베이스와의 연결, 트랜잭션 관리, 예외 처리 등의 일을 JPA가 대신 처리해줘 편리하다.

    유지보수 용이: 엔티티 클래스와 어노테이션을 사용하여 데이터베이스 스키마와 자바 객체 간의 매핑을 설정하므로, 스키마 변경 시에도 비교적 쉽게 대응할 수 있다.

    JPQL: JPA는 JPQL(Java Persistence Query Language)을 제공하여 객체 지향적인 쿼리 작성을 가능하게 한다. SQL에 비해 엔티티 객체와 필드를 사용하므로 유지보수와 가독성이 좋다.
     */

    /* 각각의 장단점
    기존의 JDBC 사용 방식의 장점
    세밀한 제어 가능: SQL을 직접 작성하고 최적화 할 수 있다.
    더 낮은 레벨의 접근: 특정한 성능 최적화가 필요한 경우에는 더 많은 제어권을 가질 수 있다.

    JPA 사용 방식의 장점
    높은 생산성: CRUD 작업이 간소화되어 개발 시간을 단축할 수 있다.
    객체 지향적: 객체와 데이터베이스 간의 매핑을 편리하게 처리할 수 있다.
    유지보수 용이: 스키마 변경에 유연하게 대응할 수 있다.

    둘 다 고려해야 할 단점
    학습 곡선: JPA는 학습이 필요한 새로운 기술일 수 있다.
    일부 특정한 케이스에서의 성능: 특정한 상황에서는 기존의 JDBC 방식이 성능면에서 더 유리할 수 있다.

    따라서 프로젝트의 요구사항, 개발 팀의 기술 수준, 성능 교수사항 등을 고려하여 JDBC 또는 JPA 중 어느 방식을 선택할 지 결정해야 한다.
     */

}
