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
