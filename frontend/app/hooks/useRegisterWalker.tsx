import React, { useState } from 'react'
import { Locales } from '../types/locales'
import { getRegisterWalkerDescription } from '../config/registerWalker'
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

export interface Props {
    lang: Locales
}

export function useRegisterWalker({ lang }: Props): RegisterWalker {
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
    const descriptionExample = getRegisterWalkerDescription(lang)

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
