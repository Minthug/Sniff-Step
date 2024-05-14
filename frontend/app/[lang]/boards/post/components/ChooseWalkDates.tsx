import React from 'react'
import { Locales } from '@/app/types/locales'

interface Props {
    days: { [key: string]: boolean }
    lang: Locales
    handleDayChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    changeDayToKorean: (lang: string, day: string) => string
}

export function ChooseWalkDates({ days, lang, handleDayChange, changeDayToKorean }: Props) {
    return (
        <div className="flex gap-4 items-center flex-wrap text-[14px]">
            {Object.keys(days).map((day) => (
                <div key={day}>
                    <input type="checkbox" id={day} name={day} value={day} checked={days[day]} onChange={handleDayChange} />
                    <label htmlFor={day}>{changeDayToKorean(lang, day)}</label>
                </div>
            ))}
        </div>
    )
}
