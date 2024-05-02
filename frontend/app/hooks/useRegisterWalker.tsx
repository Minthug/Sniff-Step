import React, { useState } from 'react'
import { Locales } from '../types/locales'
import { getRegisterWalkerDescription } from '../config/registerWalker'
import useToken from './useToken'
export const MAX_DESCRIPTION_SIZE = 3000

export interface RegisterWalker {
    days: { [key: string]: boolean }
    times: { [key: string]: boolean }
    title: string
    description: string
    descriptionSizeError: boolean
    descriptionExample: string
    showDescriptionModal: boolean
    handleDayChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    handleTimeChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    handleTitleChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    changeDayToKorean: (lang: string, day: string) => string
    changeTimeToKorean: (lang: string, time: string) => string
    handleDescriptionChange: (value: string) => void
    setShowDescriptionModal: (value: boolean) => void
    handleRegisterWalker: (file: File | null) => void
}

export interface Props {
    lang: Locales
}

export function useRegisterWalker({ lang }: Props): RegisterWalker {
    const { accessToken } = useToken()
    const [title, setTitle] = useState('')
    const [address, setAddress] = useState('안산시 상록구 건건로')
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

    const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { value } = event.target
        setTitle(value)
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

    const handleRegisterWalker = (file: File | null) => {
        const data = new FormData()
        if (file) data.append('file', file)

        data.append('title', title)
        data.append('address', address)
        data.append('description', description)
        Object.entries(days).forEach(([key, value]) => {
            if (value) data.append('activityDate', key.toUpperCase())
        })
        Object.entries(times).forEach(([key, value]) => {
            if (value) data.append('activityTime', key.toUpperCase())
        })

        fetch('/api/register-walker', {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            body: data
        })
    }

    return {
        days,
        title,
        times,
        description,
        descriptionSizeError,
        descriptionExample,
        showDescriptionModal,
        handleDayChange,
        handleTimeChange,
        handleTitleChange,
        changeDayToKorean,
        changeTimeToKorean,
        handleDescriptionChange,
        setShowDescriptionModal,
        handleRegisterWalker
    }
}
