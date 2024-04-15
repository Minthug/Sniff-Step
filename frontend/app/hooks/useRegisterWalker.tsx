import React, { useState } from 'react'
export const MAX_DESCRIPTION_SIZE = 3000

export interface RegisterWalker {
    days: { [key: string]: boolean }
    times: { [key: string]: boolean }
    description: string
    descriptionSizeError: boolean
    descriptionExample: string
    showDescriptionModal: boolean
    handleDayChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    handleTimeChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    changeDayToKorean: (lang: string, day: string) => string
    changeTimeToKorean: (lang: string, time: string) => string
    handleDescriptionChange: (value: string) => void
    setShowDescriptionModal: (value: boolean) => void
}

export function useRegisterWalker(): RegisterWalker {
    const [days, setDays] = useState<{ [key: string]: boolean }>({
        mon: false,
        tue: false,
        wed: false,
        thu: false,
        fri: false,
        sat: false,
        sun: false
    })

    const [times, setTimes] = useState<{ [key: string]: boolean }>({
        morning: false,
        afternoon: false,
        evening: false
    })
    const [description, setDescription] = useState('')
    const [descriptionSizeError, setDescriptionSizeError] = useState(false)
    const [showDescriptionModal, setShowDescriptionModal] = useState(false)
    const descriptionExample = `예시) 
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

    const handleDayChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target
        setDays((prevDays) => ({
            ...prevDays,
            [name]: checked
        }))
    }

    const handleTimeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target
        setTimes((prevTimes) => ({
            ...prevTimes,
            [name]: checked
        }))
    }

    const changeDayToKorean = (lang: string, day: string) => {
        if (lang === 'en') return day.toUpperCase()

        switch (day) {
            case 'mon':
                return '월요일'
            case 'tue':
                return '화요일'
            case 'wed':
                return '수요일'
            case 'thu':
                return '목요일'
            case 'fri':
                return '금요일'
            case 'sat':
                return '토요일'
            case 'sun':
                return '일요일'
            default:
                return ''
        }
    }

    const changeTimeToKorean = (lang: string, time: string) => {
        if (lang === 'en') return time.toUpperCase()

        switch (time) {
            case 'morning':
                return '오전'
            case 'afternoon':
                return '오후'
            case 'evening':
                return '저녁'
            default:
                return ''
        }
    }

    const handleDescriptionChange = (value: string) => {
        setDescriptionSizeError(false)
        if (value.length > MAX_DESCRIPTION_SIZE) {
            value = value.slice(0, MAX_DESCRIPTION_SIZE)
            setDescriptionSizeError(true)
        }
        setDescription(value)
    }

    return {
        days,
        times,
        description,
        descriptionSizeError,
        descriptionExample,
        showDescriptionModal,
        handleDayChange,
        handleTimeChange,
        changeDayToKorean,
        changeTimeToKorean,
        handleDescriptionChange,
        setShowDescriptionModal
    }
}
