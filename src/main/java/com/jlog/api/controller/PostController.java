package com.jlog.api.controller;

// SSR -> jsp, thymeleaf, mustache, freemarker
// -> html rendering
// SPA ->
//  vue -> vue+SSR = nuxt
//  react -> react+SSR = next
// -> javascript + < - > API (JSON)

/**
 * API 문서 생성
 *
 * GET /posts/{postId} -> 단건 조회
 * POST /posts -> 게시글 등록
 *
 * 클라이언트 입장 어떤 API 있는지 모름
 *
 * Spring RestDocs
 * - 운영코드에 -> 영향
 * - 코드 수정 -> 문서 수정 X -> 코드(기능) <!-> 문서 신뢰성 갈 수록 떨어짐
 * - Spring RestDocs 는 Test 케이스 실행 -> 문서를 생성해준다.
 */

import com.jlog.api.request.PostCreate;
import com.jlog.api.request.PostEdit;
import com.jlog.api.request.PostSearch;
import com.jlog.api.response.PostResponse;
import com.jlog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 글 등록, 글 단 건 조회, 글 리스트 조회
    // CRUD -> Create, Read, Update, Delete

    @PostMapping("/posts") // content-type -> json
    public void post(@RequestBody @Valid PostCreate request) {
        request.validate();
        postService.write(request);
    }

    /**
     * /posts -> 글 전체 조회(검색 + 페이징)
     * /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }


    // Http Methods
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    // POST Method

//    @PostMapping("/posts") // content-type -> x-www-form-urlencoded
//    public String post(@RequestParam String title, @RequestParam String content) {
//        log.info("title={}, content={}", title, content);
//        return "Hello World";
//    }

//    @PostMapping("/posts") // content-type -> x-www-form-urlencoded
//    public String post(@RequestParam Map<String, String> params) {
//        log.info("params={}", params);
//        String title = params.get("title");
//        return "Hello World";
//    }

//    @PostMapping("/posts") // content-type -> x-www-form-urlencoded
//    public String post(@ModelAttribute PostCreate params) {
//        log.info("params={}", params.toString());
//        return "Hello World";
//    }

//    @PostMapping("/posts") // content-type -> json
//    public String post(@RequestBody PostCreate params) throws Exception {
//        // 데이터를 검증하는 이유
//
//        // 1. client 개발자가 깜박할 수 있다. 실수로 값을 안보낼 수 있다.
//        // 2. client bug로 값이 누락될 수 있다.
//        // 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼 수 있다.
//        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
//        // 5. 서버 개발자의 편안함을 위해서
//        log.info("params={}", params.toString());
//
//        String title = params.getTitle();
//        if (title == null || title.equals("")) {
//            // {"title": ""}
//            // {"title": "                "}
//            // {"title": ".........수십 억 글자"}
//
//            //error
//            // 1. 빡세다. (노가다)
//            // 2. 개발팁 -> 뭔가 3번이상 반복작업을 할 때 내가 뭔가 잘못하고 있는 건 아닌 지 의심한다.
//            // 3. 누락 가능성
//            // 4. 생각보다 검증해야할 게 많다. (꼼꼼하지 않을 수 있다.)
//            // 5. 뭔가 개발자스럽지 않다. -> 폼 x
//            throw new Exception("타이틀 값이 없어요!");
//        }
//
//        String content = params.getContent();
//        if (content == null || content.equals("")) {
//            //error
//        }
//
//        return "Hello World";
//    }

//    @PostMapping("/posts") // content-type -> json
//    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) {
//        // 1. 매번 메서드마다 값을 검증 해야한다.
//        //      > 개발자가 까먹을 수 있다.
//        //      > 검증 부분에서 버그가 발생할 여지가 높다.
//        //      > 지겹다. (폼이 안난다.)
//        // 2. 응답 값에 HashMap -> 응답 클래스를 만들어 주는 게 좋습니다.
//        // 3. 여러 개의 에러처리 힘듬
//        // 4. 세 번 이상의 반복적인 작업은 피해야한다.
//          // - 코드 && 개발에 관한 모든 것
//              // - 자동화 고려
//        if (result.hasErrors()) {
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//            String fieldName = firstFieldError.getField();
//            String errorMessage = firstFieldError.getDefaultMessage();
//
//            Map<String, String> error = new HashMap<>();
//            error.put(fieldName, errorMessage);
//            return error;
//        }
//
//        log.info("params={}", params.toString());
//        // {"title": "타이틀 값이 없습니다"}
//        return Map.of();
//    }

    // Case1. 저장한 데이터 Entity -> response로 응답하기
    // Case2. 저장한 데이터의 primary_id -> response로 응답하기
    //          Client에서는 수신한 id로 글 조회 API를 통해서 글 데이터를 수신받음
    // Case3. 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 잘 관리함
    // Bad Case: 서버에서 -> 반드시 이렇게 할겁니다! fix하는 경우
    //          -> 서버에서 차라리 유연하게 대응하는게 좋습니다. -> 코드를 잘 짜야한다. ㅎ
    //          -> 한 번에 일괄적으로 잘 처리되는 케이스가 없습니다. -> 잘 관리하는 형태가 중요하다.
    // POST -> 200, 201

    // Request 클래스
    // Response 클래스

    // 클라이언트 요구사항
    // json응답에서 title값 길이를 최대 10글자로 해주세요.
    // 사실 이런 요청은 클라이언트에서 하는 게 좋다.
    // 응답 클래스 분리하세요 (서비스 정책에 맞는)

    // 조회 API
    // 지난 시간 = 단건 조회 API (1개의 글 Post를 가져오는 기능)
    // 이번 시간 = 여러개의 글을 조회 API
    // /posts
//    @GetMapping("/posts")
//    public List<PostResponse> getList(Pageable pageable) {
//        return postService.getList(pageable);
//    }
}
