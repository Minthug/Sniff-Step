import { Board } from '@/app/types/board'
import { NextRequest, NextResponse } from 'next/server'

const boards: Board[] = [
    {
        id: '1',
        title: '산본동 산책시켜드립니다',
        nickname: '베니베니',
        address: '경기도 군포시 산본동',
        description: `예시) 
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

연락처: [카카오톡 ID, 전화번호 등]`,
        activityLocation: '경기도 군포시 산본동',
        ownerSatisfaction: 10,
        imageUrl: '/images/logo1-removebg-preview.png',
        profileUrl: '/images/logo1-removebg-preview.png',
        availableDate: ['Mon', 'Tue', 'Wed', 'Thu'],
        availableTime: 'morning',
        createdAt: '2024-04-12',
        updatedAt: '2024-04-12'
    },
    {
        id: '2',
        title: '서울대입구 산책시켜드립니다',
        nickname: 'nickname',
        address: '서울특별시 관악구 봉천동',
        description: 'description',
        activityLocation: '서울특별시 관악구 봉천동',
        ownerSatisfaction: 10,
        imageUrl: '/images/text-logo-1.png',
        profileUrl: '/images/logo1-removebg-preview.png',
        availableDate: ['Mon', 'Tue', 'Wed', 'Thu'],
        availableTime: 'morning',
        createdAt: '2024-04-12',
        updatedAt: '2024-04-12'
    },
    {
        id: '3',
        title: '신림동 산책시켜드립니다',
        nickname: 'nickname',
        address: '서울특별시 관악구 신림동',
        description: 'description',
        activityLocation: '서울특별시 관악구 신림동',
        ownerSatisfaction: 10,
        imageUrl: '/images/loginSide.webp',
        profileUrl: '/images/logo1-removebg-preview.png',
        availableDate: ['Mon', 'Tue', 'Wed', 'Thu'],
        availableTime: 'morning',
        createdAt: '2024-04-12',
        updatedAt: '2024-04-12'
    }
]

export async function GET(req: NextRequest, { params }: { params: { id: string } }) {
    const findBoard = boards.find((board) => board.id === params.id)

    if (findBoard) {
        return NextResponse.json(
            {
                data: findBoard
            },
            { status: 200 }
        )
    }

    return NextResponse.json(
        {
            data: {
                message: 'Not Found'
            }
        },
        { status: 404 }
    )
}
