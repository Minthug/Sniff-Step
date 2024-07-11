import { Locale } from '@/i18n.config'

export const changeDayToKorean = (lang: Locale, day: string) => {
    if (lang === 'en') return day.toUpperCase()
    day = day.toLowerCase()

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

export const changeTimeToKorean = (lang: string, time: string) => {
    if (lang === 'en') return time.toUpperCase()

    switch (time) {
        case 'MORNING':
            return '오전'
        case 'AFTERNOON':
            return '오후'
        case 'EVENING':
            return '저녁'
        default:
            return ''
    }
}

export function convertDateFormat(time: string) {
    const date = new Date(time)
    return date.toLocaleString('ko-KR', {
        month: 'numeric',
        day: 'numeric',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: true
    })
}
