import { Locales } from '../types/locales'

export const getRegisterWalkerDescription = (lang: Locales) => {
    if (lang === 'en') return enDescription
    return koDescription
}

export const koDescription = `예시) 
산책 일정:
날짜: [산책 예정일]
시간: 오후 2시부터 오후 5시까지 (3시간)

산책 코스:
출발지: [견주님 집 주소]
목적지: [근처 공원/산책로 이름]
경로: [집에서 공원까지의 구체적인 산책 경로], 공원 내에서의 활동(뛰어놀기, 탐험하기 등) 포함
복귀: 같은 경로로 집으로 복귀

산책 활동:
기본 걷기 및 뛰기
공놀이 및 기타 활동적인 놀이
사회화: 공원 내 다른 반려견과의 상호작용 (견주님의 동의 하에)
훈련: 기본적인 명령어 연습 (앉아, 기다려, 이리 와 등)

준비사항 및 주의사항:
준비사항: 산책용 목줄, 물병, 간식, 배변 봉투
주의사항: [강아지 이름]의 특별한 주의가 필요한 건강상태나 행동특성 (예: 알레르기, 사람/다른 동물에 대한 두려움 등)

소통 및 피드백:
산책 전후로 견주님과 직접 연락을 취하여 [강아지 이름]의 상태와 특별한 요청사항을 확인합니다.
산책 중에는 사진이나 짧은 동영상을 찍어 견주님께 전달해, 안심하실 수 있도록 하겠습니다.

연락처: [카카오톡 ID, 전화번호 등]
    `

export const enDescription = `Example:
Walking Schedule:
Date: [Scheduled walking date]
Time: From 2 PM to 5 PM (3 hours)

Walking Course:
Starting Point: [Pet owner's home address]
Destination: [Nearby park/trail name]
Route: [Specific walking route from home to the park], activities in the park (running, exploring, etc.) included
Return: Return home via the same route

Walking Activities:
Basic walking and running
Playing with balls and other active games
Socialization: Interaction with other dogs in the park (with the owner's consent)
Training: Practice of basic commands (sit, wait, come, etc.)

Preparations and Cautions:
Preparations: Walking leash, water bottle, treats, poop bags
Cautions: [Dog's name]'s specific health conditions or behavioral traits requiring attention (e.g., allergies, fear of people/other animals)

Communication and Feedback:
I will contact the pet owner before and after the walk to check on [Dog's name]'s condition and any special requests.
During the walk, I will take photos or short videos to share with the owner to ensure peace of mind.

Contact: [KakaoTalk ID, phone number, etc.]

`
