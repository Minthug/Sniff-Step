import { Locales } from '@/app/types/locales'
import React from 'react'

interface Props {
    times: { [key: string]: boolean }
    lang: Locales
    handleTimeChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    changeTimeToKorean: (lang: string, day: string) => string
}

export default function ChooseWalkTimes({ times, lang, handleTimeChange, changeTimeToKorean }: Props) {
    return (
        <div className="flex gap-4 items-center flex-wrap text-[14px]">
            {Object.keys(times).map((time) => (
                <div key={time}>
                    <input type="checkbox" id={time} name={time} value={time} checked={times[time]} onChange={handleTimeChange} />
                    <label htmlFor={time}>{changeTimeToKorean(lang, time)}</label>
                </div>
            ))}
        </div>
    )
}
