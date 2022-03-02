#ERORR CODE LIST

| 코드 | 유형 | 메시지 |
|------|---|---|
|9999|unknown|알수없는 오류가 발생하였습니다.|
|1000|userNotFound|존재하지 않는 회원입니다.|
|1002|entryPointException|해당 리소스에 접근하기 위한 권한이 없습니다.|
|1003|accessDenied|보유한 권한으로 접근할수 없는 리소스 입니다.|
|1004|communicationError|통신 중 오류가 발생하였습니다.|
|1006|notOwner|해당 자원의 소유자가 아닙니다.|
|1007|resourceNotExist|요청한 자원이 존재 하지 않습니다.|
|1008|tokenExpired|토큰이 만료되었습니다.|
|1009|notExistToken|존재하지 않는 토큰입니다.|
|1010|invalidDevice|접속 허용되지 않은 기기입니다.|
|1011|alreadyAction|동일한 행동이 반복되었습니다.|
|1012|wrongPassword|패스워드가 잘못되었습니다.|
|1013|NeedLoginClip|로그인이 필요한 영상입니다.|
|1014|NotExistClip|존재하지 않는 영상입니다.|
|1015|NotUseClip|사용할 수 없는 영상입니다.|
|1016|InvalidClipPath|잘못된 접근입니다.|
|1017|ForbiddenVoteClip|투표 권한이 없습니다.|
|1018|NotExistVotingContent|투표 회차가 아닙니다.|
|1019|NotVoteClip|투표 클립 영상이 아닙니다.|
|1020|AlreadyVoteClip|이미 투표한 영상입니다.|
|1021|parameterException|파라미터 오류|

#배너
- 용어 설명
    * group
        1. 페이지에서 사용하는 모든 배너 목록을 가져오기 위한 키 값
        2. 예) 
            >clipList, clipDetail, voteClipList 등
    * bannerLocation
        1. 페이지에서 배너의 위치 위치
        2. 예) 
            >head, footer,
            contentTop, contentMiddle, contentBottom,
    * bannerType
        1. 배너가 링크를 가지고 있다면 링크의 유형
        2. 예)
            >innerScheme, outerScheme, mWeb, pWeb, aosStore, iosStore, none
    * bannerViewType
        1. 배너의 노출 유형
        2. 예)
            >html, image, text 
