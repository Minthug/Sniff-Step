import React, { useState } from 'react'
import { Locales } from '@/app/types/locales'
import { getPostDescription } from '@/app/config/post'
import { useFetch } from './useFetch'
import { useRouter } from 'next/navigation'
import { Board } from '../types/board'

const MAX_DESCRIPTION_SIZE = 3000

export interface BoardUpdateState {
    days: { [key: string]: boolean }
    times: { [key: string]: boolean }
    board: Board | undefined
    title: string
    address: string
    addressEnglish: string
    description: string
    descriptionExample: string
    showDescriptionModal: boolean
    titleError: boolean
    addressError: boolean
    dateError: boolean
    timeError: boolean
    descriptionError: boolean
    descriptionSizeError: boolean
    handleDayChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    handleTimeChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    handleTitleChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    handleChangeAddress: (address: string, addressEnglish: string) => void
    handleDescriptionChange: (value: string) => void
    changeDayToKorean: (lang: string, day: string) => string
    changeTimeToKorean: (lang: string, time: string) => string
    setShowDescriptionModal: (value: boolean) => void
    getBoardById: (id: string) => Promise<Board>
    handleUpdate: (file: File | null, id: string) => Promise<void>
    adjustBoard: (board: Board) => void
}

interface Props {
    lang: Locales
}

export function useUpdateBoard({ lang }: Props): BoardUpdateState {
    const { customFetch, isFetching } = useFetch()
    const router = useRouter()

    const [board, setBoard] = useState<Board>()
    const [title, setTitle] = useState('')
    const [address, setAddress] = useState('')
    const [addressEnglish, setAddressEnglish] = useState('')
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

    const descriptionExample = getPostDescription(lang)

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

    const handleUpdateError = (message: string[]) => {
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

    const handleChangeAddress = (address: string, addressEnglish: string) => {
        setAddress(address)
        setAddressEnglish(addressEnglish)
        setAddressError(false)
    }

    const getBoardById = async (id: string) => {
        const res = await customFetch(`/api/boards/${id}`, {
            method: 'GET'
        })

        if (!res) return

        if (!res.ok) {
            const { message } = await res.json()
            console.log(message)
        }

        const { data } = await res.json()
        setBoard(data)
        return data
    }

    const handleUpdate = async (file: File | null, id: string) => {
        if (isFetching || !board) return

        const data = new FormData()
        if (file) data.append('images', file)

        data.append('title', title)
        data.append('description', description)
        data.append('activityLocation', address)
        Object.entries(days).forEach(([key, value]) => {
            if (value) data.append('activityDate', key.toUpperCase())
        })
        Object.entries(times).forEach(([key, value]) => {
            if (value) data.append('activityTime', key.toUpperCase())
        })

        const res = await customFetch(`/api/boards/${board.id}`, {
            method: 'PATCH',
            body: data
        })

        if (res) {
            if (!res.ok) {
                const { message } = await res?.json()
                handleUpdateError(message)
                throw new Error('patch failed')
            }
            router.push(`/${lang}/boards?reload=true`)
        }
    }

    const adjustBoard = (board: Board) => {
        setTitle(board.title)
        setDescription(board.description)
        setAddress(board.activityLocation)
        setDays((prevDays) => {
            Object.keys(prevDays).forEach((key) => {
                prevDays[key] = board.activityDate.includes(key.toUpperCase())
            })
            return prevDays
        })
        setTimes((prevTimes) => {
            Object.keys(prevTimes).forEach((key) => {
                prevTimes[key] = board.activityTime.includes(key.toUpperCase())
            })
            return prevTimes
        })
    }

    return {
        days,
        title,
        times,
        board,
        address,
        addressEnglish,
        description,
        descriptionExample,
        showDescriptionModal,
        titleError,
        addressError,
        dateError,
        timeError,
        descriptionError,
        descriptionSizeError,
        handleDayChange,
        handleTimeChange,
        handleTitleChange,
        handleChangeAddress,
        handleDescriptionChange,
        changeDayToKorean,
        changeTimeToKorean,
        setShowDescriptionModal,
        getBoardById,
        handleUpdate,
        adjustBoard
    }
}
