import React, { useState } from 'react'
import { Locales } from '../types/locales'
import { getRegisterWalkerDescription } from '../config/registerWalker'
import { useFetch } from './useFetch'
import { useRouter } from 'next/navigation'
export const MAX_DESCRIPTION_SIZE = 3000

export interface RegisterWalker {
    days: { [key: string]: boolean }
    times: { [key: string]: boolean }
    title: string
    description: string
    descriptionSizeError: boolean
    descriptionExample: string
    showDescriptionModal: boolean
    titleError: boolean
    addressError: boolean
    dateError: boolean
    timeError: boolean
    descriptionError: boolean
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
    const { customFetch } = useFetch()
    const router = useRouter()

    const [title, setTitle] = useState('')
    const [address, setAddress] = useState('경기도 군포시 번영로 382')
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
    const [titleError, setTitleError] = useState(false)
    const [addressError, setAddressError] = useState(false)
    const [dateError, setDateError] = useState(false)
    const [timeError, setTimeError] = useState(false)
    const [descriptionError, setDescriptionError] = useState(false)

    const descriptionExample = getRegisterWalkerDescription(lang)

    const handleDayChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDateError(false)
        const { name, checked } = event.target
        setDays((prevDays) => ({
            ...prevDays,
            [name]: checked
        }))
    }

    const handleTimeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setTimeError(false)
        const { name, checked } = event.target
        setTimes((prevTimes) => ({
            ...prevTimes,
            [name]: checked
        }))
    }

    const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setTitleError(false)
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
        setDescriptionError(false)
        setDescriptionSizeError(false)
        if (value.length > MAX_DESCRIPTION_SIZE) {
            value = value.slice(0, MAX_DESCRIPTION_SIZE)
            setDescriptionSizeError(true)
        }
        setDescription(value)
    }

    const handleRegisterError = (message: string[]) => {
        message.find((msg: string) => {
            switch (msg) {
                case 'title should not be empty':
                    setTitleError(true)
                    return
                case 'address should not be empty':
                    setAddressError(true)
                    return
                case 'activityDate should not be empty':
                    setDateError(true)
                    return
                case 'activityTime should not be empty':
                    setTimeError(true)
                    return
                case 'description should not be empty':
                    setDescriptionError(true)
                    return
            }
        })
    }

    const handleRegisterWalker = async (file: File | null) => {
        const accessToken = localStorage.getItem('accessToken')
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

        const res = await customFetch('/api/register-walker', {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            body: data
        })

        if (res) {
            if (!res.ok) {
                const { message } = await res?.json()
                return handleRegisterError(message)
            }
            router.push(`/${lang}/boards`)
        }
    }

    return {
        days,
        title,
        times,
        description,
        descriptionSizeError,
        descriptionExample,
        showDescriptionModal,
        titleError,
        addressError,
        dateError,
        timeError,
        descriptionError,
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
