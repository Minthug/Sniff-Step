import React from 'react'
import ReactTextareaAutosize from 'react-textarea-autosize'
import { MAX_DESCRIPTION_SIZE } from '@/app/hooks/useRegisterWalker'

interface Props {
    description: string
    descriptionExample: string
    descriptionSizeError: boolean
    handleDescriptionChange: (description: string) => void
}

export function DescriptionTextarea({ description, descriptionExample, descriptionSizeError, handleDescriptionChange }: Props) {
    return (
        <>
            <ReactTextareaAutosize
                className="w-full p-4 border rounded-md resize-none outline-none"
                minRows={10}
                maxRows={50}
                value={description}
                placeholder={descriptionExample}
                onChange={(e) => handleDescriptionChange(e.target.value)}
            />
            {descriptionSizeError && <div className="text-[12px] text-[#ff0000]">최대 {MAX_DESCRIPTION_SIZE}자까지 입력 가능합니다.</div>}
        </>
    )
}
