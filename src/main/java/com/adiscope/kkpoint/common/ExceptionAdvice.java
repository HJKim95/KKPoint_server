package com.adiscope.kkpoint.common;

import com.adiscope.kkpoint.common.custom_exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;


// @ExceptionHandler, @ModelAttribute, @InitBinder 가 적용된 메서드들을 AOP를 적용해 컨트롤러 단에 적용하기 위해 고안된 애너테이션 + @ResponseBody
// 쉽게 말해서 RestController들 에러처리 해주는 곳
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionAdvice {

    // 주로 DB에 없는 정보를 Select 한 에러.
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    protected Header emptySelectException(HttpServletRequest request, Exception e) {
        log.error("INTERNAL_SERVER_ERROR : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("NoSuchElementException 에러. 주로 DB에 없는 정보를 Select 한 에러. ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 주로 유효하지 않은 SNS 토큰 에러.
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    protected Header snsTokenException(HttpServletRequest request, Exception e) {
        log.error("INTERNAL_SERVER_ERROR : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("HttpClientErrorException 에러. 주로 유효하지않은 SNS토큰 에러. ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 잘못된 리프래쉬 토큰 (우리가 발급한 엑세스 토큰) 에러.
    @ExceptionHandler(NotExistRefreshTokenException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) // 406 리프래쉬 토큰이 잘못됨 -> 새로 로그인해주세요~
    protected Header refreshTokenException(HttpServletRequest request, Exception e) {
        log.error("UNAUTHORIZED : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("리프래쉬 토큰 오류. 다시 로그인해주세요. ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 쿠폰 구매가 겹치는 경우 (동시에 쿠폰을
    // 구매하려는 경우)
    @ExceptionHandler(CouponConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    protected Header couponConflictException(HttpServletRequest request, Exception e) {
        log.error("CONFLICT : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("쿠폰 구매 충돌 오류. ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 출석체크 중복 에러
    @ExceptionHandler(UserRedundantAttendException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED) // 412
    protected Header redundantAttendException(HttpServletRequest request, Exception e) {
        log.error("PRECONDITION_FAILED : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("출석 중복 오류. ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 매칭되는 쿠폰이 없을 때 (갯수 부족)
    @ExceptionHandler(NoCouponAvailable.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED) // 412
    protected Header noCouponException(HttpServletRequest request, Exception e) {
        log.error("PRECONDITION_FAILED : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("해당 쿠폰 없음 오류. ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 네이버 이름 혹은 이메일 비동의 오류
    @ExceptionHandler(NaverInvaildNameOrEmailException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED) // 417 네이버 로그인시 약관 동의해주세요~
    protected Header naverInvaildNameOrEmailException(HttpServletRequest request, Exception e) {
        log.error("PRECONDITION_FAILED : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("네이버 이름 혹은 이메일 비동의 오류 ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 예외처리 되지않은 모든 에러.
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Header defaultException(HttpServletRequest request, Exception e) {
        log.error("INTERNAL_SERVER_ERROR : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("예외처리 되지않은 모든 에러. ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 무분별한 접근 에러. (AppInfoController)
    @ExceptionHandler(UnauthorizedVersionCheckException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected Header versionException(HttpServletRequest request, Exception e) {
        log.error("UNAUTHORIZED : ", e); // 에러 메세지 로그
        log.error("Client IP : " + getClientIP(request)); // 클라이언트 IP 로그
        return Header.ERROR("무분별한 접근 불가합니다. ErrorMessage : " + e.getMessage()); // 에러 메세지 리스폰
    }

    // 클라이언트의 IP를 알아내는 메소드
    private String getClientIP(HttpServletRequest req) {
        String header = req.getHeader("X-Forwarded-For");
        if (header == null) {
            return req.getRemoteAddr();
        }
        return header.split(",")[0];
    }


//    @ExceptionHandler(SigninFailedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    protected CommonResult defaultSignInFailedException(HttpServletRequest request, SigninFailedException e) {
//        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
//        return responseService.getFailResult(Integer.valueOf(getMessage("wrongPassword.code")), getMessage("wrongPassword.msg"));
//    }

//    @ExceptionHandler(InvalidIpException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected CommonResult invalidIpException(HttpServletRequest request, Exception e) {
//        log.error("invalidIpException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("unknown.code")), getMessage("unknown.msg") + "(" + e.getMessage() + ")");
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected CommonResult userNotFound(HttpServletRequest request, UserNotFoundException e) {
//        log.error("userNotFound : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
//    }
//
//    @ExceptionHandler(AuthenticationEntryPointException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public CommonResult authenticationEntryPointException(HttpServletRequest request, AuthenticationEntryPointException e) {
//        log.error("authenticationEntryPointException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
//        log.error("accessDeniedException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
//    }
//
//    @ExceptionHandler(CommunicationException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public CommonResult communicationException(HttpServletRequest request, CommunicationException e) {
//        log.error("communicationException : ", e);
//        return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
//    }
//
//    @ExceptionHandler(NotOwnerException.class)
//    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
//    public CommonResult notOwnerException(HttpServletRequest request, NotOwnerException e) {
//        log.error("notOwnerException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("notOwner.code")), getMessage("notOwner.msg"));
//    }
//
//    @ExceptionHandler(ResourceNotExistException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public CommonResult resourceNotExistException(HttpServletRequest request, ResourceNotExistException e) {
//        log.error("resourceNotExistException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("resourceNotExist.code")), getMessage("resourceNotExist.msg"));
//    }
//
//    @ExceptionHandler(RefreshTokenExpiredException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public CommonResult resourceTokenExpiredException(HttpServletRequest request, RefreshTokenExpiredException e) {
//        log.error("resourceTokenExpiredException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("tokenExpired.code")), getMessage("tokenExpired.msg"));
//    }
//
//    @ExceptionHandler(NotExistRefreshTokenException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public CommonResult resourceNotExistTokenException(HttpServletRequest request, NotExistRefreshTokenException e) {
//        log.error("resourceNotExistTokenException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("notExistToken.code")), getMessage("notExistToken.msg"));
//    }
//
//    @ExceptionHandler(InvalidDeviceException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public CommonResult resourceInvalidDeviceException(HttpServletRequest request, InvalidDeviceException e) {
//        log.error("resourceInvalidDeviceException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("invalidDevice.code")), getMessage("invalidDevice.msg"));
//    }
//
//    @ExceptionHandler(ParameterException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public CommonResult parameterExceptionException(HttpServletRequest request, ParameterException e) {
//        log.error("ParameterException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("parameterException.code")), getMessage("parameterException.msg"));
//    }
//
//    @ExceptionHandler(AlreadyActionException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public CommonResult resourceAlreadyActionException(HttpServletRequest request, AlreadyActionException e) {
//        log.error("resourceAlreadyActionException : " + e.toString());
//        return responseService.getFailResult(Integer.valueOf(getMessage("alreadyAction.code")), getMessage("alreadyAction.msg"));
//    }
//
//    @ExceptionHandler(ClipException.class)
//    public CommonResult clipException(HttpServletRequest request, HttpServletResponse response, ClipException e) {
//        log.error("clipException : " + e.toString());
//        CommonResult commonResult = null;
//        if (e instanceof NeedLoginClipException) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("NeedLoginClip.code")), getMessage("NeedLoginClip.msg"));
//
//        } else if (e instanceof NotExistClipException) {
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("NotExistClip.code")), getMessage("NotExistClip.msg"));
//
//        } else if (e instanceof NotUseClipException) {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("NotUseClip.code")), getMessage("NotUseClip.msg"));
//
//        } else if (e instanceof InvalidClipPathException) {
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("InvalidClipPath.code")), getMessage("InvalidClipPath.msg"));
//
//        } else {
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("unknown.code")), getMessage("unknown.code"));
//
//        }
//
//        return commonResult;
//    }
//
//    @ExceptionHandler(VoteException.class)
//    public CommonResult voteException(HttpServletRequest request, HttpServletResponse response, VoteException e) {
//        log.error("voteException : " + e.toString());
//
//        CommonResult commonResult = null;
//        if (e instanceof ForbiddenVoteClipException) {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("ForbiddenVoteClip.code")), getMessage("ForbiddenVoteClip.msg"));
//
//        } else if (e instanceof NotExistVotingContentException) {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("NotExistVotingContent.code")), getMessage("NotExistVotingContent.msg"));
//
//        } else if (e instanceof NotVoteClipException) {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("NotVoteClip.code")), getMessage("NotVoteClip.msg"));
//
//        } else if (e instanceof AlreadyVoteClipException) {
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("AlreadyVoteClip.code")), getMessage("AlreadyVoteClip.msg"));
//
//        } else {
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("unknown.code")), getMessage("unknown.code"));
//
//        }
//
//        return commonResult;
//    }
//
//    @ExceptionHandler(YoutubeException.class)
//    public CommonResult youtubeException(HttpServletRequest request, HttpServletResponse response, YoutubeException e) {
//        log.error("youtubeException : " + e.toString());
//        CommonResult commonResult = null;
//        if (e instanceof NotExistVideoException) {
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("NotExistClip.code")), getMessage("NotExistClip.msg"));
//
//        } else {
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            commonResult = responseService.getFailResult(Integer.valueOf(getMessage("unknown.code")), getMessage("unknown.code"));
//
//        }
//
//        return commonResult;
//    }
//
//    // code정보에 해당하는 메시지를 조회합니다.
//    private String getMessage(String code) {
//        return getMessage(code, null);
//    }
//
//    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
//    private String getMessage(String code, Object[] args) {
//        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
//    }
}
